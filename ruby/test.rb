#!/usr/bin/env ruby
# cassandra.io ruby helper library tests

require './cassandraio'

# cIO = CassandraIO.new(
   #:heroku => 'api.cassandra.io', 
      #:port => 443, 
         #:ssl => 'true',
            #:token => '<token>',
               #:accountId => '<accountId>')
               
cIO = CassandraIO.new(:token => '<token>', :accountId => '<accountId>')

# Test comparator types
# puts 'UTF8Type = ' + CassandraIO.UTF8Type
# puts 'LongType = ' + CassandraIO.LongType
# puts 'CounterType = ' + CassandraIO.CounterType
# puts 'TimeUUIDType = ' + CassandraIO.TimeUUIDType

# Test validation types

# puts cIO.setup()

# puts cIO.setupaccount()

# puts cIO.create_keyspace('keyspace')
# {"message":"Success","status":"200","detail":"Keyspace added successfully."}
# {"message":"Failed","status":"400","detail":"Request denied, max number of keyspaces in plan reached."}

# puts cIO.delete_keyspace('keyspace')
# {"message":"Success","status":"200","detail":"Keyspace dropped successfully."}

# puts cIO.create_columnfamily('keyspace', 'countercolumnfamily', CassandraIO.CounterType)
# {"message":"Success","status":"200","detail":"columnfamily ColumnFamily created successfully"}
# {"message":"Failed","status":"400","detail":"Request denied, max number of column familes in plan reached."}

# puts cIO.get_columnfamilies('keyspace')
# {"columnfamilies":[{"columnfamily":{"sortedby":"UTF8Type","name":"CF_METADATA"},"columns":[]},{"columnfamily":{"sortedby":"UTF8Type","name":"columnfamily"},"columns":[]}]}

# puts cIO.get_columnfamily('keyspace', 'columnfamily')
# {"columnfamily":{"sortedby":"UTF8Type","name":"columnfamily"},"columns":[]}

# puts cIO.delete_columnfamily('keyspace', 'columnfamily')
# {"message":"Success","status":"200","detail":"columnfamily dropped."}

# puts cIO.create_column('keyspace', 'columnfamily', 'nonindexedcolumn', CassandraIO.LongType)
# {"message":"Success","status":"200","detail":"nonindexedcolumn Column created successfully"}

# puts cIO.create_indexed_column('keyspace', 'columnfamily', 'indexedcolumn', CassandraIO.UTF8Type)
# {"message":"Success","status":"200","detail":"indexedcolumn Column created successfully"}

# puts cIO.remove_indexed_column('keyspace', 'columnfamily', 'indexedcolumn', CassandraIO.UTF8Type)
# {"message":"Success","status":"200","detail":"indexedcolumn Column created successfully"}

# puts cIO.post_data('keyspace', 'columnfamily', 'rowkey', {'key1' => 'value', 'key2' => 'value'}, :ttl => 60)
# {"message":"Success","status":"200","detail":"rowkey upsert successfull."}

# puts cIO.post_data('keyspace', 'columnfamily', 'rowkey', {'key3' => 'value', 'key4' => 'value'})
# {"message":"Success","status":"200","detail":"rowkey upsert successfull."}

data = 
'{
	"rowkeys": [
    	{
        	"rowkey": "rowkey",
			"columns": [
            	{
                	"columnname": "cn",
                    "columnvalue": "cv",
                    "ttl" : 0
                },
                {
                	"columnname": "cn",
                    "columnvalue": "cv",
                    "ttl" : 0
                }
            ]
		},
        {
         	"rowkey": "anotherrowkey",
          	"columns": [
            	{
                	"columnname": "cn",
                    "columnvalue": "cv"
                },
                {
                	"columnname": "cn",
                	"columnvalue": "cv"
                }
       		]
     	}
  	]
}'

# puts cIO.bulkpost_data('keyspace', 'columnfamily', data)
# {"message":"Success","status":"200","detail":"Bulk upload successfull."}

# puts cIO.get_data('keyspace', 'columnfamily', 'rowkey')
# {"key4":"value","key3":"value","key2":"value","key1":"value"}

# puts cIO.get_paginatable_data('keyspace', 'columnfamily', 'rowkey', 'key', 1)
# {"cn":"cv"}

# puts cIO.delete_column('keyspace', 'columnfamily', 'rowkey', 'key1')
# {"message":"Success","status":"200","detail":"Deleted entry successfully."}

# puts cIO.delete_row('keyspace', 'columnfamily', 'anotherrowkey')
# {"message":"Success","status":"200","detail":"Deleted entry successfully."}

# puts cIO.query_cql('keyspace', 'columnfamily', 'select *')
# {"anotherrowkey":{"KEY":"anotherrowkey"},"rowkey":{"KEY":"rowkey","cn":"cv","key3":"value","key4":"value"}}

# puts cIO.increment_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1', 1)
# {"message":"Success","status":"200","detail":"Counter updated."}

# puts cIO.decrement_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1', 1)
# {"message":"Success","status":"200","detail":"Counter updated."}

# puts cIO.get_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1')
# {"counter1":0}