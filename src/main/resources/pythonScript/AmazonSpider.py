# -*- coding:utf-8 -*-

import re
import requests
import sys
import time
import urllib
#import urllib2

#import SpiderDatabase
import SpiderMySqlDatabase
from utils.JsonResponseToClient import JsonResponseToClient
from utils.ScarabaeusEnum import ResponseEnum
from utils.ScarabaeusEnum import SourceEnum
from utils.Utils import TimeUtils
#reload(sys)
#sys.setdefaultencoding('utf-8')

datas = {
            'showViewpoints':1,
            'sortBy':'recent',
            'pageNumber':0,
        }
class AmazonSpider():
    
    def __init__(self, url, num = 10):
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
        url_temp = re.search(r"(.*?)/dp/(.*?)/(.*?)",url)
        url2 = url_temp.group(2)
        return url2
        
    def get_commentUrl(self,url,page):
        url_temp = re.search(r"(.*?)/dp/(.*?)/(.*?)",url)
        url1 = url_temp.group(1)
        url2 = url_temp.group(2)
        ref_o = {
            'ref':'cm_cr_getr_d_paging_btm_',
            }
        datas['pageNumber'] += 1
        ref = urllib.parse.urlencode(ref_o)
        data = urllib.parse.urlencode(datas)
        url = url1+'/product-reviews/'+url2+'/'+ref+str(page)+'?'+data
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
                
    def getAmazonCommnet(self):
        baseurl = 'https://www.amazon.cn'
        p = 1
        while True:
            forinsert = []
            if self.state is 'True':
                break
            
            comment_url = self.get_commentUrl(self.url, p)
            headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36'}
            html = self.get_htmlviaCom(comment_url, headers)
            #answer = html.decode("UTF-8","replace")          
            answer = html
            results = re.findall('<div id=\".*?\" data-hook=\"review\" class=\"a-section review\">.*</div>', answer)
            if(len(results) == 0):
                break
            for result in results:
                try:
                    result = result.replace("\n","")      
                    star = re.search('<span class=\"a-icon-alt\">(.*?)</span>',result)
                    title = re.search('<a data-hook=\"review-title\" class=\"a-size-base a-link-normal review-title a-color-base a-text-bold\" href=\"(.*?)\">(.*?)</a>',result )
                    content = re.search('<span data-hook=\"review-body\" class=\"a-size-base review-text\">(.*?)</span>',result)
                    timen = re.search('<span data-hook=\"review-date\" class=\"a-size-base a-color-secondary review-date\">' + u'于' + ' (.*?)</span>',result)
                    type = re.search(u'产品款式' + ': (.*?)</a>',result)
                    star_comment = star.group(1)
                    subject = title.group(2).strip()
                    comment = content.group(1).strip()
                    comment = re.sub('<.*?>','',comment)
                    time_comment = timen.group(1).strip()
                    type_comment = ''
                    if(type != None):
                        type_comment = type.group(1).strip()
                        type_comment = re.sub('<.*?>','',type_comment)
                    time1 = time.mktime(time.strptime(time_comment,u'%Y年%m月%d日'))
                    time_comment = TimeUtils.convert_timestamp_to_date(time1)
                    row = (subject,comment,star_comment,time_comment,type_comment,self.url)
                    forinsert.append(row)                          
                except:
                    continue        
            p += 1
            self.mdatabase.insert_values(forinsert)
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.Amazon.value, "success")
        JsonResponseToClient.generate_json_response(resp)
        
    def getAmazonViaTime(self):
        baseurl = 'https://www.amazon.cn'
        p = 1
        timestamp = time.mktime(time.strptime(self.lasttime,'%Y%m%d%H%M%S'))
                    
        skiptime = "False"
        while True:
            forinsert = []
            if self.state is 'True':
                break
            comment_url = self.get_commentUrl(self.url, p)
            headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36'}
            html = self.get_htmlviaCom(comment_url, headers)
            #answer = html.decode("UTF-8","replace")
            answer = html
            results = re.findall('<div id=\".*?\" data-hook=\"review\" class=\"a-section review\">.*</div>', answer)
            if(len(results) == 0):
                break
            for result in results:
                result = result.replace("\n","")   
                star = re.search('<span class=\"a-icon-alt\">(.*?)</span>',result)                    
                title = re.search('<a data-hook=\"review-title\" class=\"a-size-base a-link-normal review-title a-color-base a-text-bold\" href=\"(.*?)\">(.*?)</a>',result )
                content = re.search('<span data-hook=\"review-body\" class=\"a-size-base review-text\">(.*?)</span>',result)
                timen = re.search('<span data-hook=\"review-date\" class=\"a-size-base a-color-secondary review-date\">' + u'于' + ' (.*?)</span>',result)
                type = re.search(u'产品款式' + ': (.*?)</a>',result)
                star_comment = star.group(1)
                subject = title.group(2).strip()
                comment = content.group(1).strip()
                comment = re.sub('<.*?>','',comment)
                time_comment = timen.group(1).strip()
                type_comment = ''
                if(type != None):
                    type_comment = type.group(1).strip()
                    type_comment = re.sub('<.*?>','',type_comment)

                time1 = time.mktime(time.strptime(time_comment,u'%Y年%m月%d日'))
                time_comment = TimeUtils.convert_timestamp_to_date(time1)
                if time1 < float(timestamp):
                    skiptime = "True"
                    break
                row = (subject,comment,star_comment,time_comment,type_comment,self.url)
                forinsert.append(row)                      
            self.mdatabase.insert_values(forinsert)                    
            if skiptime is 'True':
                break
            p += 1
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.Amazon.value, "success")
        JsonResponseToClient.generate_json_response(resp)
    
    def launch(self, proxyIP = "", ProxyPort = "", continueTask='False'):
        self.proxyIP = proxyIP
        self.ProxyPort = ProxyPort
        
        self.productId = self.getProductId(self.url)
        
        #createtable = 'title text, firstcomment text,star text,date char,referenceName text'
        createtable = 'id int(11) NOT NULL AUTO_INCREMENT,title VARCHAR(200),firstcomment VARCHAR(20000),star VARCHAR(50),date VARCHAR(20),referenceName VARCHAR(200),link VARCHAR(200), PRIMARY KEY(id)'
        self.mdatabase = SpiderMySqlDatabase.SpiderMySqlDatabase(self.url)
        self.mdatabase.connect()
        dbname = self.mdatabase.load_database_name()
        result = self.mdatabase.create_or_select_database()
        if result == 1:
            tablename = self.mdatabase.load_table_name()
            self.mdatabase.create_table_with_column(createtable)
            if self.searchState() is 'True' and continueTask is 'True':
                self.getAmazonViaTime()
            else:
                self.getAmazonCommnet()
        else:
            self.mdatabase.disconnect()
    
    @staticmethod
    def getPreSearchTime(productID):
        return self.mdatabase.get_time()
    
    @staticmethod
    def getGoodsId(url):
        url_temp = re.search(r"(.*?)/dp/(.*?)/(.*?)",url)
        url2 = url_temp.group(2)
        return url2
        
    @staticmethod
    def getSpider(url):
        Instance = AmazonSpider(url)
        return Instance

if __name__ == '__main__':
   url ='https://www.amazon.cn/dp/B071FT7K92/ref=sr_1_1?ie=UTF8&qid=1517364861&sr=8-1&keywords=S8'
   if len(sys.argv) > 1:
       url = sys.argv[1]
   galaxy = AmazonSpider(url)
   galaxy.launch('','','True')

