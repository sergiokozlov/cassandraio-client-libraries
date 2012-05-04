<?php
/* cassandra.io php helper library */
class CassandraIO {

  /* Comparator types */
  define('UTF8Type', 'UTF8Type');
  define('LongType', 'LongType');
  define('CounterType', 'CounterType');
  define('TimeUUIDType', 'TimeUUIDType');
  
  function request($method, $url, $paramsArray) {
    $curl_handle = curl_init();
    curl_setopt($curl_handle, CURLOPT_URL, $url);
    curl_setopt($curl_handle, CURLOPT_CONNECTTIMEOUT, 2);
    curl_setopt($curl_handle, CURLOPT_USERPWD, $this->token . ':' . $this->accountId);
    curl_setopt($curl_handle, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt($curl_handle, CURLOPT_RETURNTRANSFER, 1);
    if ($method == 'GET')
      curl_setopt($curl_handle, CURLOPT_DNS_USE_GLOBAL_CACHE, FALSE);
    if ($method == 'POST') {
      curl_setopt($curl_handle, CURLOPT_POST, 1);
      curl_setopt($curl_handle, CURLOPT_POSTFIELDS, $paramsArray);
    }
    if ($method == 'DELETE') {
      curl_setopt($curl_handle, CURLOPT_CUSTOMREQUEST, 'DELETE');
      curl_setopt($curl_handle, CURLOPT_POSTFIELDS, $paramsArray);
    }
    $buffer = curl_exec($curl_handle);
    $error  = curl_error($curl_handle);
    curl_close($curl_handle);
    if (empty($buffer)) {
      return "Error: " . $error;
    } else {
      return $buffer;
    }
  }
  
  function urlBuilder() {
    for ($i = 0; $i < func_num_args(); $i++) {
      if ($i == 0) {
        $url = 'https:/api.cassandra.io/1' . func_get_arg($i);
      } else {
        $url = $url . func_get_arg($i);
      }
    }
    return $url;
  }
  
  function getUrlBuilder($paramsArray) {
    $count = 0;
    foreach ($paramsArray as $key => $value) {
      if ($value != NULL) {
        if ($count == 0) {
          $url = '?' . $key . '=' . $value;
        } else {
          $url = $url . '&' . $key . '=' . $value;
        }
        $count++;
      }
    }
    return $url;
  }
  
  /* POST  /v0.1/setup */
  function setup() {
    return $this->request('POST', $this->urlBuilder('/setup'), NULL);
  }
  
  /* POST  /v0.1/setupaccount */
  function setupaccount() {
    return $this->request('POST', $this->urlBuilder('/setupaccount'), NULL);
  }
  
  /* POST  /v0.1/keyspace/{kName} */
  function create_keyspace($kName) {
    $params = array(
    );
    return $this->request('POST', $this->urlBuilder('/keyspace', $kName), $params);
  }
  
  /* DELETE  /v0.1/keyspace/{kName} */
  function delete_keyspace($kName) {
    return $this->request('DELETE', $this->urlBuilder('/keyspace', $kName), NULL);
  }
  
  /* GET  /v0.1/columnfamily/{kName}/? */
  function get_columnfamilies($kName) {
    return $this->request('GET', $this->urlBuilder('/columnfamily', $kName), NULL);
  } 
  
  /* GET  /v0.1/columnfamily/{kName}/{cfName}/? */
  function get_columnfamily($kName, $cfName) {
    return $this->request('GET', $this->urlBuilder('/columnfamily', $kName, $cfName), NULL);
  }
  
  /* POST  /v0.1/columnfamily/{kName}/{cfName}/{cType}/? */
  function create_columnfamily($kName, $cfName, $cType) {
    return $this->request('POST', $this->urlBuilder('/columnfamily', $kName, $cfName, $cfType), NULL);
  }
  
  /* DELETE  /v0.1/columnfamily/{kName}/{cfName}/? */
  function delete_columnfamily($kName, $cfName) {
    return $this->request('DELETE', $this->urlBuilder('/columnfamily', $kName, $cfName), NULL);
  }
  
  /* POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/? */
  function create_column($kName, $cfName, $cName, $cType) {
    return $this->request('POST', $this->urlBuilder('/column', $kName, $cfName, $cName, $cType), NULL);
  }
  
  /* POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/? >isIndexed=true */
  function create_indexed_column($kName, $cfName, $cName, $cType) {
    $params = array(
      'isIndexed' => 'true'
    );
    return $this->request('POST', $this->urlBuilder('/column', $kName, $cfName, $cName, $cType), $params);
  }
  
  /* POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/? >isIndexed=false */
  function remove_indexed_column($kName, $cfName, $cName, $cType) {
    $params = array(
      'isIndexed' => 'false'
    );
    return $this->request('POST', $this->urlBuilder('/column', $kName, $cfName, $cName, $cType), $params);
  }
  
  /* POST  /v0.1/data/{kName}/{cfName}/{rowKey}/? */
  function post_data($kName, $cfName, $rowKey, $data, $ttl) {
    $params = array(
    
    );
    if ($ttl)
      $params['ttl'] = $ttl;
    return $this->request('POST', $this->urlBuilder('/data', $kName, $cfName, $rowKey), $params);
  }
  
  /* POST  /v0.1/data/{kName}/{cfName}/? >body=<json> */
  function bulkpost_data() {
    
  }
  
  /* GET  /v0.1/data/{kName}/{cfName}/{rowKey}/? */
  function get_data($kName, $cfName, $rowKey) {
    return $this->request('GET', $this->urlBuilder('/data', $kName, $cfName, $rowKey), NULL);
  }
  
  /* GET /v0.1/data/{kName}/{cfName}/{rowKey}?limit=(limit num of results)&fromKey=(key to start range) */
  function get_paginatable_data($kName, $cfName, $rowKey, $fromKey, $limit) {
    $params = array(
      'fromKey' => $fromKey,
      'limit' => $limit
    );
    return $this->request('GET', $this->urlBuilder('/data', $kName, $cfName, $rowKey, $this->getUrlBuilder($params)), NULL);
  }
  
  /* DELETE  /v0.1/data/{kName}/{cfName}/{rowKey}/{cName}/? */
  function delete_column($kName, $cfName, $rowKey, $cName) {
    return $this->request('DELETE', $this->urlBuilder('/data', $kName, $cfName, $rowKey, $cName), NULL);
  }
  
  /* DELETE  /v0.1/data/{kName}/{cfName}/{rowKey}/? */
  function delete_row($kName, $cfName, $rowKey) {
    return $this->request('DELETE', $this->urlBuilder('/data', $kName, $cfName, $rowKey), NULL);
  }
  
  /* GET  /v0.1/cql/{kName}/{cfName}/? */
  function query_cql($kName, $cfName, $query) {
    $params = array(
      'select' => $query
    );
    return $this->request('GET', $this->urlBuilder('/cql', $kName, $cfName, $this->getUrlBuilder($params)), NULL);
  }
  
  /* POST /v0.1/counter/{kName}/{cfName}/{rowKey}/?columnName=(your counter column name)&count=(your incremented or decremented value) */
  function increment_count(kName, $cfName, $rowKey, $columnName, $count) {
    $params = array(
      'count' => '-' . $count
    );
    return $this->request('POST', $this->urlBuilder('/counter', $kName, $cfName, $rowKey, $columnName, $count), $params);
  }
  
  /* POST /v0.1/counter/{kName}/{cfName}/{rowKey}/{columnName}?count=(your incremented or decremented value) */
  function decrement_count(kName, $cfName, $rowKey, $columnName, $count) {
    $params = array(
      'count' => '-' . $count
    );
    return $this->request('POST', $this->urlBuilder('/counter', $kName, $cfName, $rowKey, $columnName, $count), $params);
  }
  
  /* GET /v0.1/counter/{kName}/{cfName}/{rowKey}/{columnName}?count=(your incremented or decremented value) */
  function get_count($kName, $cfName, $rowKey, $columnName) {
    return $this->request('GET', $this->urlBuilder('/counter', $kName, $cfName, $rowKey, $columnName), NULL);
  }
  
}
?>