# -*- coding:utf-8 -*-
'''

Created on 2018/2/13

@author : xxfore

'''
from utils.ScarabaeusEnum import ResponseEnum
from utils.ScarabaeusEnum import SourceEnum
import json
class JsonResponseToClient(object):
    def __init__(self, result = ResponseEnum.fail.value, type = SourceEnum.Other.value,  message = "no data"):
      self.result = result
      self.type = type
      self.message = message

    def obj_2_json(self,rfu):
        return {
            "result": self.result,
            "type": self.type,
            "message": self.message
            }
        
    @staticmethod
    def generate_json_response(resp):
        ##Have 3 ways to pase json string
        #1.
        print(json.dumps(resp, default=resp.obj_2_json))
        #2.
        #print(json.dumps(resp,default=lambda obj: obj.__dict__, sort_keys=True, indent=4))
        #3.
        #print(resp.__dict__)