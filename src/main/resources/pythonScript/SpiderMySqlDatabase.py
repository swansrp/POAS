# -*- coding:utf-8 -*-
'''

Created on 2018/2/2

@author : xxfore

'''
import re
import sys
import os
import inspect
import json
#import MySQLdb
import pymysql as MySQLdb
MySQLdb.install_as_MySQLdb()

sys.dont_write_bytecode = True
class SpiderMySqlDatabase():
    def __init__(self,url):
        self.url = url

    def connect(self):
        '''
        Connect local MySql server.
        Doesnt specify database here
        '''
        try:
            self.db = MySQLdb.connect("106.14.115.34","root","admin",use_unicode=True, charset="utf8")
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def test(self,sql):
        '''
        just for test
        '''
        cursor = self.db.cursor()
        try:
            cursor.execute(sql)
            results = cursor.fetchall()
            for row in results:
                model_id = row[0]
                model_name = row[1]
        except:
            bye
    
    def load_database_name(self):
        '''
        Use DB:Configuration Table:urlmap_
        to get main database which need create or select later
        Attention : This operation will select DB:Configuration first.
        So if want to do any SQL operation on other DB, please use {@link #create_or_select_database} first
        '''
        try:
            self.db.select_db('Configuration')
            cursor = self.db.cursor()
            sql = "select modelmap_.model_name \
                   from urlmap_ \
                   inner join modelmap_ \
                   on modelmap_.id = urlmap_.model_id \
                   where urlmap_.url = '%s' " % \
                   (self.url)
            cursor.execute(sql)
            result = cursor.fetchone()
            self.dbname = result[0]
            return self.dbname
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def create_or_select_database(self):
        '''
        Select exist database which named by model_name
        if database not exist , create it.
        '''
        try:
            self.db.select_db(self.dbname)
            return 1
        except:
            cursor = self.db.cursor()
            cursor.execute("create database if not exists " + self.dbname)
            try:
                self.db.select_db(self.dbname)
            except MySQLdb.Error as e:
                self.print_sql_error(self.get_func_name(),e)
                return 0

    def load_table_name(self):
        '''
        Load source en name to create table.
        Attention : This operation will select DB:Configuration first.
        So if want to do any SQL operation on other DB, please use {@link #create_or_select_database} first
        '''
        try:
            self.db.select_db('Configuration')
            cursor = self.db.cursor()
            sql = "select sourcemap_.source_en \
                   from urlmap_ inner \
                   join sourcemap_ \
                   on sourcemap_.id = urlmap_.source_id \
                   where urlmap_.url = '%s' " % \
                   (self.url)
            cursor.execute(sql)
            result = cursor.fetchone()
            self.tablename = result[0]
            return self.tablename
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def create_table_with_column(self,column):
        '''
        If database exist, select it, then create table with specify column
        Param Type:
            column:String
        '''
        try:
            self.db.select_db(self.dbname)
            cursor = self.db.cursor()
            sql = 'create table if not exists ' + self.tablename + ' (' + column + ') CHARSET=utf8'
            cursor.execute(sql)
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def insert_values(self,value):
        '''
        Insert values after call #create_table_with_column
        Just use for the table which have PRIMARY KEY(id) AUTO_INCREMENT in first column
        Param Type:
            value:list
        '''
        sql = ''
        if len(value) == 0:
            return
        self.db.select_db(self.dbname)
        cursor = self.db.cursor()
        #self.db.set_character_set('utf8')
        cursor.execute('SET NAMES utf8;') 
        cursor.execute('SET CHARACTER SET utf8;')
        cursor.execute('SET character_set_connection=utf8;')
        for x in value:
            format_list = []
            for i in range(len(x)):
                format_list.append(x[i].replace('\'','').replace('\"',''))
            sql = 'insert into ' + self.tablename + ' values (null,"' + '","'.join(format_list) + '",-1,0)'
            try:
                cursor.execute(sql)
            except MySQLdb.Error as e:
                self.print_sql_error(self.get_func_name(),e)
                continue
        self.db.commit() 
        
    def get_time(self):
        '''
        Get last_fetch_time from Configuration.urlmap_ by url
        '''
        try:
            self.db.select_db('Configuration')
            cursor = self.db.cursor()
            sql = "select last_fetch_time \
                   from urlmap_ \
                   where url = '%s' " % \
                   (self.url)
            cursor.execute(sql)
            result = cursor.fetchone()
            self.time = result[0]
            return self.time
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def update_time(self,value):
        '''
        Update last_fetch_time from Configuration.urlmap_ by url
        '''
        try: 
            self.db.select_db('Configuration')
            cursor = self.db.cursor()
            sql = "update urlmap_ \
                   set last_fetch_time = %s \
                   where url = '%s' " % \
                  (value,self.url)
            cursor.execute(sql)
            self.db.commit()
        except MySQLdb.Error as e:
            self.print_sql_error(self.get_func_name(),e)

    def load_database_version(self):
        '''
        Load current select DB version
        '''
        cursor = self.db.cursor()
        cursor.execute("SELECT VERSION()")
        data = cursor.fetchone()

    def disconnect(self):
        self.db.close()
    
    def print_sql_error(self,func,e):
        sqlError = "\033[31mError \033[35min func:%s is %d:%s\033[0m" % (func,e.args[0],e.args[1])
        print (sqlError)

    def get_func_name(self):
       '''
       Returns the function name who call it.
       Format(func_name,line)
       '''
       try:
           raise Exception
       except:
           f = sys.exc_info()[2].tb_frame.f_back
       #return sys._getframe().f_code.co_name
       return (f.f_code.co_name,f.f_lineno)

if __name__=="__main__":
    import SpiderMySqlDatabase
    print (help(SpiderMySqlDatabase))