# -*- coding:utf-8 -*-
import re
import requests
import sys
import urllib
#import urllib2
import time

#import SpiderDatabase
import SpiderMySqlDatabase
from utils.JsonResponseToClient import JsonResponseToClient
from utils.ScarabaeusEnum import ResponseEnum
from utils.ScarabaeusEnum import SourceEnum
from utils.Utils import TimeUtils
from utils.Utils import StringUtils

#reload(sys)
#sys.setdefaultencoding('utf-8')
datas = {
    'fid':'',  
}
class JifengSpider():
    
    def __init__(self, url, num = 100 ):
        self.url = url
        self.ProxyPort =''
        self.proxyIP =''
        self.productId = ""
        self.pagenum = num
    
    def searchState(self):
        flag = 'False'
        self.lasttime = self.mdatabase.get_time()
        if self.lasttime is not '0':
            flag = 'True'
        self.updatetime()
        return flag
        
    def updatetime(self):
        todays = time.strftime('%Y%m%d%H%M%S',time.localtime())
        self.mdatabase.update_time(str(todays))        

    def getProductId(self,url):

        url = re.sub('http://bbs.gfan.com/forum-','',url)
        url = re.sub('-(.*?).html','',url)
        return url
        
    def get_htmlviaCom(self,url,header):
        try:
            http_proxy = str("http://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            https_proxy = str("https://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            proxyDict = {
              "http"  : http_proxy,
              "https" : https_proxy
            }
            if len(self.proxyIP) is 0:
                r = requests.get(url,timeout=60,headers=header)
                r.encoding='utf-8'
                return r.text
            else:
                proxy_handler = urllib2.ProxyHandler(proxyDict)
                opener = urllib2.build_opener(proxy_handler)
                urllib2.install_opener(opener)
                request = urllib2.Request(url,headers=header)
                response = urllib2.urlopen(request)  
                content = response.read()
                response.close()
                #answer = content.decode("UTF-8","replace").encode("UTF-8")
                answer = content
                return answer
        except:
            return " ERROR " 
    
    def get_html(self,url):
        try:
            headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7'}
            r = requests.get(url,timeout=30,headers = headers)
            r.raise_for_status()
            r.encoding='utf-8'
            return r.text
        except:
            return " ERROR "
        
        
    def getJifengComment(self,viatime):
        
        headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36'}
        try:
            html = self.get_htmlviaCom(self.url, headers)
            #answer = html.decode("UTF-8","replace").encode("UTF-8")
            answer = html
            pageNum = re.search(r'<span title="共 (.*?) 页"> / .*? 页</span>', answer)
            totalPage = int(pageNum.group(1)) + 1
            p_group = re.search(r'http://bbs.gfan.com/forum-(.*?)-1.html', self.url)
            datas['fid'] = p_group.group(1)
            timestamp = time.mktime(time.strptime(self.lasttime,'%Y%m%d%H%M%S'))
        except:
            return
        #for p in range(1, totalPage):#全部版
        skiptime = "False"
        if totalPage < self.pagenum:
            self.pagenum = totalPage
        for p in range(1,self.pagenum):#演示版 
            forinsert = []        
            data=urllib.parse.urlencode(datas)
            new_url = 'http://bbs.gfan.com/forum.php?mod=forumdisplay&'+data+'&orderby=dateline&filter=author&page='+str(p)
            html = self.get_htmlviaCom(new_url, headers)  
            #answer = html.decode("UTF-8","replace")
            answer = html
            baseurl = 'http://bbs.gfan.com/android-'
                       
            pattern = re.compile('<tbody id="normalthread_.*?">.*?</tbody>',re.S)
            results = re.findall(pattern, answer)   
            if len(results) == 0:
                break         
            time_rule = re.compile("<td class=\"by\">.*?</td>",re.S)
            title_rule = re.compile('<a href=\"http://bbs.gfan.com/.*?\".*?onclick=\"atarget\(this\)\" class=\"xst\" >(.*?)</a>', re.S)
            for result in results:
                try:

                    result = result.replace("\n","")
                    time_g = re.search(time_rule,result)
                    posttime = time_g.group()
                    posttime = re.sub("\n", "",posttime)
                    posttime = re.sub("\n\r", "",posttime)
                    posttime = re.sub("\r", "",posttime)
                    posttime = re.sub("<cite>.*?</cite>", "",posttime)
                    posttime = re.sub("<.*?>","",posttime)
                    title = re.search(title_rule,result)
                    href = re.search('<tbody id="normalthread_(.*?)">',result)
                    link = baseurl + str(href.group(1)) + '-1-1.html'
                    subject = title.group(1).strip()
                    time1 = time.mktime(time.strptime(posttime,'%Y-%m-%d %H:%M'))
                    posttime = TimeUtils.convert_timestamp_to_date(time1)
                    if viatime and time1 < float(timestamp):
                        skiptime ="True"
                        break
                    subject = StringUtils.remove_emoji_from_string(subject)
                    row = (subject,'',posttime,link)
                    forinsert.append(row)                              
                except:
                    continue        
            self.mdatabase.insert_values(forinsert)
            if viatime and skiptime is 'True':
                break
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.JiFeng.value, "success")
        JsonResponseToClient.generate_json_response(resp)

    def launch(self, proxyIP = "", ProxyPort = "", continueTask='False'):
        self.proxyIP = proxyIP
        self.ProxyPort = ProxyPort
       
        self.productId = self.getProductId(self.url)
        
        #createtable = 'firstcomment text,date char,link text'
        createtable = 'id int(11) NOT NULL AUTO_INCREMENT,title VARCHAR(200),firstcomment VARCHAR(5000),date VARCHAR(20),link VARCHAR(500),sentiment int(11) DEFAULT -1,category int(11) DEFAULT 0, PRIMARY KEY(id)'
        self.mdatabase = SpiderMySqlDatabase.SpiderMySqlDatabase(self.url)
        self.mdatabase.connect()
        dbname = self.mdatabase.load_database_name()
        result = self.mdatabase.create_or_select_database()
        if result == 1:
            tablename = self.mdatabase.load_table_name()
            self.mdatabase.create_table_with_column(createtable)
            self.getJifengComment(self.searchState() is 'True' and continueTask is 'True')
        else:
            self.mdatabase.disconnect()

    @staticmethod
    def getPreSearchTime(productID):
        return self.mdatabase.get_time()

    @staticmethod
    def getGoodsId(url):

        url = re.sub('http://bbs.gfan.com/forum-','',url)
        url = re.sub('-(.*?).html','',url)
        return url

    @staticmethod
    def getSpider(url):
        Instance = JifengSpider(url)
        return Instance

if __name__ == '__main__':
   url ='http://bbs.gfan.com/forum-1691-1.html'
   if len(sys.argv) > 1:
       url = sys.argv[1]
   galaxy = JifengSpider(url)
   galaxy.launch('','','True')