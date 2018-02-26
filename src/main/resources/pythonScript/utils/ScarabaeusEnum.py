# -*- coding:utf-8 -*-
'''

Created on 2018/2/13

@author : xxfore

'''
from enum import Enum, unique
import sys
sys.dont_write_bytecode = True
@unique
class ResponseEnum(Enum):
    success = 0
    fail = 1
    
@unique
class SourceEnum(Enum):
    Amazon = 0
    Baidu = 1
    GalaxyClub = 2
    JiFeng = 3
    JingDong = 4
    TaoBao = 5
    Tmall = 6
    Other = 7