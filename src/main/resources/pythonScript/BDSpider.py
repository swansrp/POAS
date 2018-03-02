# -*- coding:utf-8 -*-
'''
Created on 2017/12/04

@author: zhouyanghua
'''
import re
import requests
import sys
import time
#import urllib2

#import SpiderDatabase
import SpiderMySqlDatabase
from utils.JsonResponseToClient import JsonResponseToClient
from utils.ScarabaeusEnum import ResponseEnum
from utils.ScarabaeusEnum import SourceEnum
from utils.Utils import TimeUtils
from utils.Utils import StringUtils

#reload(sys)
#sys.setdefaultencoding('utf-8')

class BaiduSpider():

    def __init__(self, url, num = 30 ):
        self.url = url
        self.proxyIP = ""
        self.ProxyPort = ""
        self.state = "False"
        self.productId = ''
        self.pagenums = num
        #self.header = 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36'

    def setProxy(self):
        enable_proxy=True

        proxy_handler = urllib2.ProxyHandler({'http' : '109.130.2.36:8080','https' : '109.130.2.36:8080'})
        nullproxy = urllib2.ProxyHandler({})
        if enable_proxy:
            opener = urllib2.build_opener(proxy_handler)
        else:
            opener = urllib2.build_opener(nullproxy)

        urllib2.install_opener(opener)
        
    def setState(self,value):
        self.state = value

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
        try:
            #result = self.get_htmlviaCom(url).decode("UTF-8","replace")
            result = self.get_htmlviaCom(url)
            product_content = re.search('<title>(.*?)-(.*?)</title>',result)
            productId = product_content.group(1)
            return productId
        except:
            return 'error'
        
    def get_html(self,url):
        try:
            r = requests.get(url,timeout=30)
            r.raise_for_status()
            r.encoding='utf-8'
            return r.text
        except:
            return " ERROR "
        
    def get_htmlviaCom(self,url):
        try:       
            header = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7'}  
            http_proxy = str("http://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            https_proxy = str("https://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            proxyDict = {
              "http"  : http_proxy,
              "https" : https_proxy
            }

            if len(self.proxyIP) is 0:
                r = requests.get(url,timeout=60,headers=header)
            else:
                r = requests.get(url,headers = header,proxies = proxyDict,timeout = 60,verify=False)
            r.raise_for_status()
            r.encoding='utf-8'
            return r.text
        except:
            return " ERROR "
    
    def getTiebaComment(self):
        
        baseurl = 'http://tieba.baidu.com'
        pattern = re.compile('<li class=" j_thread_list clearfix".*?</li>',re.S)
        
        for num in range(0,self.pagenums):
            forinsert = []
            if self.state is 'True':
                break
                
            url1 = self.url + '&pn='
            url1 = url1+str(num*50)

            #html = self.get_htmlviaCom(url1).decode("UTF-8","replace")
            html = self.get_htmlviaCom(url1)
            results = re.findall(pattern, html)

            title_rule = re.compile('a rel=(.*?) href=\"(.*?)\" title=\"(.*?)\" target=', re.S)
            postinfo_rule =re.compile('<span class=\"threadlist_rep_num center_text\"(.*?)>(.*?)</span>',re.S)
            for result in results:
                try:
                    if self.state is 'True':
                        break
                    result = result.replace("\n","")
                    num = re.search(postinfo_rule,result)
                    title = re.search(title_rule,result )
                
                    #链接地址， 帖子主题， 帖子内容， 回复数
                    link = baseurl + str(title.group(2))
                    subject = title.group(3).strip()
                    replynum = num.group(2).strip()
                    #answer = self.get_htmlviaCom(link).decode("UTF-8","replace")
                    answer = self.get_htmlviaCom(link)
                    context = re.search(r'<cc>(.*?)</cc>',answer).group(1)
                    context = re.sub("<span(.*?)span>", "",context)
                    context = re.sub("\n", "",context)
                    context = re.sub("\n\r", "",context)
                    context = re.sub("\r", "",context)
                    context = re.sub(" ", "",context)
                    context = re.sub("<.*?>", "",context)
                    #createtime = re.search(r'date&quot;:&quot;(.*?)&quot',answer).group(1)
                    createtime = re.search(r'date&quot;:&quot;(.*?)&quot',answer)
                    if createtime == None :
                        createtime = re.search(r'<span class="tail-info">' + u'1楼' + '</span><span class="tail-info">(.*?)</span>',answer)
                    createtime = createtime.group(1)
                    time1 = time.mktime(time.strptime(createtime,'%Y-%m-%d %H:%M'))
                    createtime = TimeUtils.convert_timestamp_to_date(time1)
                    context = StringUtils.remove_emoji_from_string(context)
                    row = (subject,context,replynum,createtime,link)
                    forinsert.append(row)
                except:
                    continue
            self.mdatabase.insert_values(forinsert)
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.Baidu.value, "success")
        JsonResponseToClient.generate_json_response(resp)
            
    def getTiebaviaTime(self):
        
        baseurl = 'http://tieba.baidu.com'
        pattern = re.compile('<li class=" j_thread_list clearfix".*?</li>',re.S)

        timestamp = time.mktime(time.strptime(self.lasttime,'%Y%m%d%H%M%S'))
         
        for num in range(0,self.pagenums):
            forinsert = []
            if self.state is 'True':
                break
                
            url1 = self.url + '&pn='
            url1 = url1+str(num*50)

            #html = self.get_htmlviaCom(url1).decode("UTF-8","replace")
            html = self.get_htmlviaCom(url1)
            results = re.findall(pattern, html)

            title_rule = re.compile('a rel=(.*?) href=\"(.*?)\" title=\"(.*?)\" target=', re.S)
            postinfo_rule =re.compile('<span class=\"threadlist_rep_num center_text\"(.*?)>(.*?)</span>',re.S)
            for result in results:
                try:
                    if self.state is 'True':
                        break
                    result = result.replace("\n","")
                    num = re.search(postinfo_rule,result)
                    title = re.search(title_rule,result )

                    #链接地址， 帖子主题， 帖子内容， 回复数
                    link = baseurl + str(title.group(2))
                    subject = title.group(3).strip()
                    replynum = num.group(2).strip()
                    #answer = self.get_htmlviaCom(link).decode("UTF-8","replace")
                    answer = self.get_htmlviaCom(link)
                    #createtime = re.search(r'date&quot;:&quot;(.*?)&quot',answer).group(1)
                    createtime = re.search(r'date&quot;:&quot;(.*?)&quot',answer)
                    if createtime == None :
                        createtime = re.search(r'<span class="tail-info">' + u'1楼' + '</span><span class="tail-info">(.*?)</span>',answer)
                    createtime = createtime.group(1)
                    time1 = time.mktime(time.strptime(createtime,'%Y-%m-%d %H:%M'))
                    createtime = TimeUtils.convert_timestamp_to_date(time1)
                    if time1 < float(timestamp):
                        continue
                    context = re.search(r'<cc>(.*?)</cc>',answer).group(1)
                    context = re.sub("<span(.*?)span>", "",context)
                    context = re.sub("\n", "",context)
                    context = re.sub("\n\r", "",context)
                    context = re.sub("\r", "",context)
                    context = re.sub(" ", "",context)
                    context = re.sub("<.*?>", "",context)
                    context = StringUtils.remove_emoji_from_string(context)
                    row = (subject,context,replynum,createtime,link)
                    forinsert.append(row)
                except:
                    continue
            self.mdatabase.insert_values(forinsert)
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.Baidu.value, "success")
        JsonResponseToClient.generate_json_response(resp)

    def launch(self, proxyIP = "", ProxyPort = "", continueTask='False'):
        self.proxyIP = proxyIP
        self.ProxyPort = ProxyPort
        
        self.productId = self.getProductId(self.url)
        if(self.productId == 'error'):
            return
        
        #createtable = 'title text, firstcomment text,replynum char,date char,link text'
        createtable = 'id int(11) NOT NULL AUTO_INCREMENT,title VARCHAR(200),firstcomment VARCHAR(5000),replynum VARCHAR(50),date VARCHAR(20),link VARCHAR(500),sentiment int(11) DEFAULT -1,category int(11) DEFAULT 0, PRIMARY KEY(id)'
        self.mdatabase = SpiderMySqlDatabase.SpiderMySqlDatabase(self.url)
        self.mdatabase.connect()
        dbname = self.mdatabase.load_database_name()
        result = self.mdatabase.create_or_select_database()
        if result == 1:
            tablename = self.mdatabase.load_table_name()
            self.mdatabase.create_table_with_column(createtable)
            if self.searchState() is 'True' and continueTask is 'True':
                self.getTiebaviaTime()
            else:
                self.getTiebaComment()
        else:
            self.mdatabase.disconnect()            

    @staticmethod
    def getPreSearchTime(productID):
        return self.mdatabase.get_time()
    
    @staticmethod
    def getGoodsId(url):
        return 'tieba'
        
    @staticmethod
    def getSpider(url):
        Instance = BaiduSpider(url)
        return Instance


if __name__ == '__main__':
   url ='http://tieba.baidu.com/f?kw=%E4%B8%89%E6%98%9Fs8&ie=utf-8'
   if len(sys.argv) > 1:
       url = sys.argv[1]
   galaxy = BaiduSpider(url)
   galaxy.launch('','','True')