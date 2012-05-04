# cassandraio-php

A PHP helper library for a RESTful interface with a CassandraIO application.

## Prerequisites

* php

## Usage

Include the library:

```
include('cassandraio');

```

Use with your Heroku application:

```
$cIO = new CassandraIO(array(
   'heroku' => 'api.cassandra.io', 
     'port' => '443', 
       'ssl' => 'true', 
         'token' => '<token>', 
           'accountId' => '<accountId>'));
```

Or more simply:

```
$cIO = new CassandraIO('<token>', '<accountId'>);
```

### Parameters

* `heroku` OPTIONAL. The application URL of your Heroku add-on. Defaults to the cassandra.io app URL.
* `port` OPTIONAL. Defaults to 443.
* `ssl` OPTIONAL. Defaults to true.
* `token` Basic authentication username found in Heroku add-on settings.
* `accountId` Basic authentication password found in Heroku add-on settings.

## Validation Types
* `CassandraIO.UTF8Type` 
* `CassandraIO.LongType` 
* `CassandraIO.CounterType`
* `CassandraIO.TimeUUIDType`

### RESTful API

Below are examples for using the exposed calls.


To create a keyspace:


    $cIO->create_keyspace('keyspace')
    
 
To delete a keyspace:


    $cIO->delete_keyspace('keyspace')
    
    
To create a column family:


    $cIO->create_columnfamily('keyspace', 'countercolumnfamily', CassandraIO.CounterType)
    
    
To view column families:


    $cIO->get_columnfamilies('keyspace')
    

To view a column family:


    $cIO->get_columnfamily('keyspace', 'columnfamily')
    
    
To delete a column family:


    $cIO->delete_columnfamily('keyspace', 'columnfamily')
    

To specify a column:


    $cIO->create_column('keyspace', 'columnfamily', 'nonindexedcolumn', CassandraIO.LongType)
    

To index a column:


    $cIO->create_indexed_column('keyspace', 'columnfamily', 'indexedcolumn', CassandraIO.UTF8Type)
    
    
To remove an index from a column:


    $cIO->remove_indexed_column('keyspace', 'columnfamily', 'indexedcolumn', CassandraIO.UTF8Type)
    
 
To post data with ttl attributes:


    $cIO->post_data('keyspace', 'columnfamily', 'rowkey', array('key1' => 'value', 'key2' => 'value'), :ttl => 60)
    
    
To post data:


    $cIO->post_data('keyspace', 'columnfamily', 'rowkey', array('key3' => 'value', 'key4' => 'value'))
    

To bulk post data:


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

    $cIO->bulkpost_data('keyspace', 'columnfamily', data)
    
   
To view data:
  
  
    $cIO->get_data('keyspace', 'columnfamily', 'rowkey')
    
    
To paginate data:


    $cIO->get_paginatable_data('keyspace', 'columnfamily', 'rowkey', 'key', 1)
    
   
To delete data:


    $cIO->delete_column('keyspace', 'columnfamily', 'rowkey', 'key1')
    
    
To delete a row of data:

 
    $cIO->delete_row('keyspace', 'columnfamily', 'anotherrowkey')
    
    
To query for data:


    $cIO->query_cql('keyspace', 'columnfamily', 'select *')
    
    
To increment a counter:


    $cIO->increment_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1', 1)
    
    
To decrement a counter:


    $cIO->decrement_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1', 1)
    
    
To get the value of a counter:


    $cIO->get_count('keyspace', 'countercolumnfamily', 'rowkey', 'counter1')