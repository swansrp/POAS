# -*- coding:utf-8 -*-
'''

Created on 2018/2/23

@author : xxfore

'''
import time
import sys
import re
sys.dont_write_bytecode = True
class TimeUtils(object):
    @staticmethod
    def convert_timestamp_to_date(timestamp):
        time_local = time.localtime(timestamp)
        dt = time.strftime("%Y-%m-%d %H:%M:%S",time_local)
        return dt
    
class StringUtils(object):
    @staticmethod
    def remove_emoji_from_string(text):
        co = re.compile(u'[\U00010000-\U0010ffff]')
        return co.sub(u'',text)