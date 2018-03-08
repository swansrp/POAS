# -*- coding:utf-8 -*-
'''
Created on 2018/1/5

@author: zhouyanghua
'''
import json
import random
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

#reload(sys)
#sys.setdefaultencoding('utf-8')

class TaoBaoSpider():

    def __init__(self, url, num = 100 ):
        self.url = url
        self.proxyIP = ""
        self.ProxyPort = ""
        self.productId = ""
        self.pagenum = num

    def choiceUseragent(self):
        user_agent_list = [\
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 "
            "(KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",
            "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 "
            "(KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 "
            "(KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 "
            "(KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 "
            "(KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 "
            "(KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 "
            "(KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "
            "(KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 "
            "(KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 "
            "(KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"
           ]

        ua = random.choice(user_agent_list)
        return ua

    def setProxy(self):
        enable_proxy=True

        proxy_handler = urllib2.ProxyHandler({'http' : '109.130.2.36:8080','https' : '109.130.2.36:8080'})
        nullproxy = urllib2.ProxyHandler({})
        if enable_proxy:
            opener = urllib2.build_opener(proxy_handler)
        else:
            opener = urllib2.build_opener(nullproxy)

        urllib2.install_opener(opener)

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

        temp=[];
        temp.append(url)

        if url.find('taobao') != -1  or url.find('tmall') != -1:
            for s in range(0,len(temp)):
                if '?id' in temp[s]:
                    id=temp[s].split('?id=')[-1].split('&')[0]
                elif '&id' in temp[s]:
                    id=temp[s].split('&id=')[-1].split('&')[0]
                elif 'itemId=' in temp[s]:
                    id=temp[s].split('itemId=')[-1].split('&')[0]
                else:
                    id=temp[s].split('?')[0].split('/')[-1].split('.')[0][1:]

                userid = ''
                if 'user_id=' in temp[s]:
                    userid = temp[s].split('user_id=')[-1].split('&')[0]

            return id
        elif url.find('jd') != -1:
            websites = url.split('/')
            return websites[-1].split('.')[0]

    def getSellerId(self,url):
        try:
            headers = {'User-Agent':self.choiceUseragent()}
            html = self.get_htmlviaCom(url,headers)
            rel = '<meta name="microscope-data" (.*?)>'
            context = re.findall(rel,html)
            uidrul = 'userid=(.*?);'
            uid = re.findall(uidrul, str(context))

            return uid[0]
        except:
            return 0

    def getCommentUrl(self,url):

        base_url = url
        if  url.find('taobao') != -1:
            base_url ='https://rate.taobao.com/feedRateList.htm?auctionNumId=%s&userNumId=%s&currentPageNum=%s&orderType=feedbackdate&folded=0'
        elif  url.find('tmall') != -1:
            base_url = 'https://rate.tmall.com/list_detail_rate.htm?itemId=%s&sellerId=%s&content=1&order=1&currentPage=%s'
        elif url.find('jd') != -1:
            base_url ='https://sclub.jd.com/comment/productPageComments.action?productId=%s&score=0&sortType=6&page=%s&pageSize=10&isShadowSku=0&fold=1'

        return base_url

    def get_html(self,url,hearder):
        try:
            r = requests.get(url,timeout=60,headers=hearder)
            return r.text
        except:
            return " ERROR "

    def get_htmlviaCom(self,url,hearder):
        try:

            http_proxy = str("http://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            https_proxy = str("https://")+ str(self.proxyIP)+":"+str(self.ProxyPort)
            proxyDict = {
              "http"  : http_proxy,
              "https" : https_proxy
            }

            if len(self.proxyIP) is 0:
                r = requests.get(url,timeout=60,headers=hearder)
            else:
                r = requests.get(url,headers = hearder,proxies = proxyDict,timeout = 60,verify=False)
#            r.raise_for_status()
#            r.encoding='utf-8'
            return r.text
        except:
            return " ERROR "

    def getTaobaoComment(self,viatime):
        time1 = time.time()
        commenturl = self.getCommentUrl(self.url)
        sellerid = self.getSellerId(self.url)
        timestamp = '0'
        if self.lasttime is not '0':
            timestamp = time.mktime(time.strptime(self.lasttime,'%Y%m%d%H%M%S'))   
        
        for k in range(1, self.pagenum):
            skiptime = "False"
            forinsert = []
            time3 = time.time()
            url = commenturl % (self.productId, sellerid, k)
            headers = {'User-Agent': self.choiceUseragent()}
            #             res = urllib2.Request(url, headers =headers)
            #             response = urllib2.urlopen(res)
            #             content = response.read()
            #             response = self.get_html(url,headers)

            try:
                response = self.get_htmlviaCom(url, headers)
                #tbjson = response.decode('utf-8', 'ignore').strip().replace('(', '').replace(')', '')
                tbjson = response.strip().replace('(', '').replace(')', '')
                #tbjson = json.loads(tbjson, "utf-8")
                tbjson = json.loads(tbjson)
                tbcomment = tbjson['comments']
                currentpage = tbjson['currentPageNum']
                totalpage = tbjson['maxPage']
            except:
                continue

                #             print content
                #
                #             #构建标准Json数据
                #             tbjson = content.decode('gbk','ignore').strip().replace('(','').replace(')','')
                #
                #             try:
                #                 tbjson = json.loads(tbjson,"utf-8")
                #             except:
                #                 continue

            for hot in tbcomment:
                username = hot['user']
                name = username['nick']
                comment1 = hot['content']
                date1 = hot['date']
                product = hot['auction']
                productinfo = product['sku']
                appendc = hot['appendList']
                time1 = time.mktime(time.strptime(date1,u'%Y年%m月%d日 %H:%M'))
                date1 = TimeUtils.convert_timestamp_to_date(time1)
                if viatime and time1 < float(timestamp):
                    skiptime ="True"
                    break
                if appendc:
                    comment2 = appendc[0]['content']  # 追评内容
                    date2 = appendc[0]['dayAfterConfirm']  # 几天后追评
                else:
                    comment2 = ''
                    date2 = ''
                replay = hot['reply']
                if replay:
                    replay = replay['content']
                else:
                    replay = ''
                row = (name,comment1,date1,comment2,str(date2),replay,productinfo,self.url)
                forinsert.append(row)
            self.mdatabase.insert_values(forinsert)
            if currentpage == totalpage :
                break
            if viatime and skiptime is 'True':
                break
        time4 = time.time()
        resp = JsonResponseToClient(ResponseEnum.success.value, SourceEnum.TaoBao.value, "success")
        JsonResponseToClient.generate_json_response(resp)

    def launch(self, proxyIP = "", ProxyPort = "", continueTask='False'):
        self.proxyIP = proxyIP
        self.ProxyPort = ProxyPort

        self.productId = self.getProductId(self.url)
        
        #createtable = 'username text, firstcomment text,date char,appendComment text, appenddate char,reply text,referenceName text'
        createtable = 'id int(11) NOT NULL AUTO_INCREMENT,username VARCHAR(200),firstcomment VARCHAR(5000),date VARCHAR(20),appendComment VARCHAR(5000), appenddate VARCHAR(20),reply VARCHAR(5000),referenceName VARCHAR(200),link VARCHAR(500),sentiment int(11) DEFAULT -1,category int(11) DEFAULT 0, PRIMARY KEY(id)'
        self.mdatabase = SpiderMySqlDatabase.SpiderMySqlDatabase(self.url)
        self.mdatabase.connect()
        dbname = self.mdatabase.load_database_name()
        result = self.mdatabase.create_or_select_database()
        if result == 1:
            tablename = self.mdatabase.load_table_name()
            self.mdatabase.create_table_with_column(createtable)
            self.getTaobaoComment(self.searchState() is 'True' and continueTask is 'True')
        else:
            self.mdatabase.disconnect()

    @staticmethod
    def getPreSearchTime(productID):   
        return self.mdatabase.get_time()

    @staticmethod
    def getGoodsId(url):

        temp=[];
        temp.append(url)

        if url.find('taobao') != -1  or url.find('tmall') != -1:
            for s in range(0,len(temp)):
                if '?id' in temp[s]:
                    id=temp[s].split('?id=')[-1].split('&')[0]
                elif '&id' in temp[s]:
                    id=temp[s].split('&id=')[-1].split('&')[0]
                elif 'itemId=' in temp[s]:
                    id=temp[s].split('itemId=')[-1].split('&')[0]
                else:
                    id=temp[s].split('?')[0].split('/')[-1].split('.')[0][1:]

                userid = ''
                if 'user_id=' in temp[s]:
                    userid = temp[s].split('user_id=')[-1].split('&')[0]

            return id

    @staticmethod
    def getSpider(url):
        Instance = TaoBaoSpider(url)
        return Instance

if __name__ == '__main__':
   url='https://item.taobao.com/item.htm?spm=a230r.1.14.20.3ec658behZCwEk&id=551783548295&ns=1&abbucket=9'
   if len(sys.argv) > 1:
       url = sys.argv[1]
   galaxy = TaoBaoSpider(url)
   galaxy.launch('','','True')


