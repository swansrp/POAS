# -*- coding:utf-8 -*-
'''

Created on 2018/2/23

@author : xxfore

'''
import time

class TimeUtils(object):
    @staticmethod
    def convert_timestamp_to_date(timestamp):
        time_local = time.localtime(timestamp)
        dt = time.strftime("%Y-%m-%d %H:%M:%S",time_local)
        return dt