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

#reload(sys)
#sys.setdefaultencoding('utf-8')

class GalaxyClubSpider():
    def __init__(self, url, num = 5 ):
        self.url = url
        self.ProxyPort =''
        self.proxyIP =''
        self.state = "False"
        self.productId = ""
        self.pagenum = num
        
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
        url = re.sub('-p(.*?).html','',url)
        url = re.sub('http://www.galaxyclub.cn/bbs/','',url)
        url = re.sub('.html','',url)
        return url
    
    def get_html(self,url):
        try:
            headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7'}
            r = requests.get(url,timeout=30,headers = headers)
            r.raise_for_status()
            r.encoding='utf-8'
            return r.text
        except:
            return " ERROR "  
        
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

                return r.text
            else:
                proxy_handler = urllib2.ProxyHandler(proxyDict)
                opener = urllib2.build_opener(proxy_handler)
                urllib2.install_opener(opener)
                request = urllib2.Request(url,headers=header)
                response = urllib2.urlopen(request)  
                content = response.read()
                response.close()

                return content
        except:
            return " ERROR "    
              
    def getGalaxyClubComment(self):
        
        headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36'}
        html = self.get_htmlviaCom(self.url, headers)
        #answer = html.decode("UTF-8","replace")
        answer = html
        page_total = re.search(r"<span class=\'last-no\'>.*?</span>",answer)
        total = page_total.group()
        total = re.sub('<.*?>','',total)
        #for page_num in range(1,int(total)+1):#全部版
        for page_num in range(1,self.pagenum):#演示版
            forinsert = []
            if self.state is 'True':
                break
            url_base = re.sub(".html","",self.url)
            url_last = url_base+'-0-last-0'+'-p'+str(page_num)+".html"
            html = self.get_htmlviaCom(url_last, headers)
            #answer = html.decode("UTF-8","replace")
            answer = html
            li_format = re.compile('<li class=\"\">.*?</li>',re.S)
            li_content = re.findall(li_format,answer)
            if len(li_content) == 0:
                break
            for li in li_content:
                try:
                    if self.state is 'True':
                        break
                    #time.sleep(2)
                    href = re.search(r'<a href=\"(.*?)\" class=\"tit\" .*?>(.*?)</a>',li)
                    url_detail = "http://www.galaxyclub.cn"+href.group(1)
                    link = url_detail

                    html = self.get_htmlviaCom(url_detail, headers)
                    #answer = html.decode("UTF-8","replace")
                    answer = html
                    theme_content = href.group(2)
                    theme_content = re.sub("&quot;", "\"",theme_content)

                    time_content = re.search(r"<p class=\"data\">(.*?)</p>", answer)
                    if time_content == None:
                        time_content =" "
                    else:
                        time_content = time_content.group(1)

                    time1 = time.mktime(time.strptime(time_content,'%Y-%m-%d %H:%M'))
                    time_content = TimeUtils.convert_timestamp_to_date(time1)
                    content_format = re.compile('<div class=\"board-cont\">.*?</div>',re.S)
                    c_content = re.search(content_format,answer)
                    if c_content == None:
                        c_content = " "
                    else:
                        content_content = c_content.group()
                        content_content = re.sub("\n", "",content_content)
                        content_content = re.sub("\n\r", "",content_content)
                        content_content = re.sub("\r", "",content_content)
                        content_content = re.sub(" ", "",content_content)
                        content_content = re.sub("<divclass=\"Hid_cont\">.*?</div>", "",content_content)
                        content_content = re.sub("<.*?>", "",content_content)
                        content_content = re.sub("&nbsp;", "",content_content)
                        content_content = re.sub("&quot;", "\"",content_content)
                        content_content = re.sub("&#39;", "",content_content)
                    row = (theme_content,content_content,time_content,link)
                    forinsert.append(row)
                except:
                    continue        
            self.mdatabase.insert_values(forinsert)
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.GalaxyClub.value, "success")
        JsonResponseToClient.generate_json_response(resp)
        
    def getGalaxyClubViaTime(self):

        headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36'}
        html = self.get_htmlviaCom(self.url, headers)
        #answer = html.decode("UTF-8","replace")
        answer = html
        page_total = re.search(r"<span class=\'last-no\'>.*?</span>",answer)
        total = page_total.group()
        total = re.sub('<.*?>','',total)
        #for page_num in range(1,int(total)+1):#全部版
        timestamp = time.mktime(time.strptime(self.lasttime,'%Y%m%d%H%M%S'))
        skiptime = "False"
        for page_num in range(1,self.pagenum):#演示版 
            forinsert = []        
            if self.state is 'True':
                break
            url_base = re.sub(".html","",self.url)
            url_last = url_base+'-0-last-0'+'-p'+str(page_num)+".html"
            html = self.get_htmlviaCom(url_last, headers)
            #answer = html.decode("UTF-8","replace")
            answer = html
            li_format = re.compile('<li class=\"\">.*?</li>',re.S)
            li_content = re.findall(li_format,answer)
            if len(li_content) == 0:
                break
            for li in li_content:
                try:
                    if self.state is 'True':
                        break
                    #time.sleep(2)
                    href = re.search(r'<a href=\"(.*?)\" class=\"tit\" .*?>(.*?)</a>',li)
                    url_detail = "http://www.galaxyclub.cn"+href.group(1)
                    link = url_detail

                    html = self.get_htmlviaCom(url_detail, headers)
                    #answer = html.decode("UTF-8","replace")
                    answer = html
                    theme_content = href.group(2)
                    theme_content = re.sub("&quot;", "\"",theme_content)

                    time_content = re.search(r"<p class=\"data\">(.*?)</p>", answer)
                    if time_content == None:
                        time_content =" "
                    else:
                        time_content = time_content.group(1)
                    
                    time1 = time.mktime(time.strptime(time_content,'%Y-%m-%d %H:%M'))
                    time_content = TimeUtils.convert_timestamp_to_date(time1)
                    if time1 < float(timestamp):
                        skiptime ="True"
                        break
                    
                    content_format = re.compile('<div class=\"board-cont\">.*?</div>',re.S)
                    c_content = re.search(content_format,answer)
                    if c_content == None:
                        c_content = " "
                    else:
                        content_content = c_content.group()
                        content_content = re.sub("\n", "",content_content)
                        content_content = re.sub("\n\r", "",content_content)
                        content_content = re.sub("\r", "",content_content)
                        content_content = re.sub(" ", "",content_content)
                        content_content = re.sub("<divclass=\"Hid_cont\">.*?</div>", "",content_content)
                        content_content = re.sub("<.*?>", "",content_content)
                        content_content = re.sub("&nbsp;", "",content_content)
                        content_content = re.sub("&quot;", "\"",content_content)
                        content_content = re.sub("&#39;", "",content_content)
                    row = (theme_content,content_content,time_content,link)
                    forinsert.append(row)                
                except:
                        continue   
            self.mdatabase.insert_values(forinsert)                        
            if skiptime is 'True':
                break
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.GalaxyClub.value, "success")
        JsonResponseToClient.generate_json_response(resp)
   
    def launch(self, proxyIP = "", ProxyPort = "", continueTask='False'):
        self.proxyIP = proxyIP
        self.ProxyPort = ProxyPort
        
        self.productId = self.getProductId(self.url)
        
        #createtable = 'title text, firstcomment text,date char,link text'
        createtable = 'id int(11) NOT NULL AUTO_INCREMENT,title VARCHAR(200),firstcomment VARCHAR(20000),date VARCHAR(20),link VARCHAR(200), PRIMARY KEY(id)'
        self.mdatabase = SpiderMySqlDatabase.SpiderMySqlDatabase(self.url)
        self.mdatabase.connect()
        dbname = self.mdatabase.load_database_name()
        result = self.mdatabase.create_or_select_database()
        if result == 1:
            tablename = self.mdatabase.load_table_name()
            self.mdatabase.create_table_with_column(createtable)
            if self.searchState() is 'True' and continueTask is 'True':
                self.getGalaxyClubViaTime()
            else:
                self.getGalaxyClubComment()
        else:
            self.mdatabase.disconnect()

    @staticmethod
    def getPreSearchTime(productID):   
        return self.mdatabase.gettime()

    @staticmethod
    def getGoodsId(url):

        url = re.sub('-p(.*?).html','',url)
        url = re.sub('http://www.galaxyclub.cn/bbs/','',url)
        url = re.sub('.html','',url)
        return url

    @staticmethod
    def getSpider(url):
        Instance = GalaxyClubSpider(url)
        return Instance

if __name__ == '__main__':
   url ='http://www.galaxyclub.cn/bbs/galaxys_s8.html'
   if len(sys.argv) > 1:
       url = sys.argv[1]
   galaxy = GalaxyClubSpider(url)
   galaxy.launch('','','True')