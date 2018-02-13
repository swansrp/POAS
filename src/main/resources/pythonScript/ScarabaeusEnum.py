# -*- coding:utf-8 -*-
'''

Created on 2018/2/13

@author : xxfore

'''
from enum import Enum, unique

@unique
class ResponseEnum(Enum):
    success = 0
    fail = 1