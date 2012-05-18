#!/usr/bin/env ruby
# cassandra.io ruby helper library

require 'rubygems'
require 'json'
require 'net/http'
require 'uri'
require 'rest-client'
require 'httpclient'

# Override standard module to remove POST param escaping
module Net
  module HTTPHeader
    def post_url_builder(postParams)
      queryUrl = ''
      if (postParams.nil? or postParams == 0)
        # Null or empty item
      else
        count = 0
        postParams.each_pair do |key,value|
          if (count == 0)
            queryUrl = queryUrl + key + '=' + value
            count = count + 1
          else
            queryUrl = queryUrl + '&' + key + '=' + value
          end
        end
      end
      return queryUrl
    end

    def set_form_data(postParams, sep = '&')
      self.body = post_url_builder(postParams)
      self.content_type = 'application/x-www-form-urlencoded'
    end
    alias form_data= set_form_data
  end
end

class CassandraIO

  # Comparator types
  @@UTF8Type = 'UTF8Type'
  @@LongType = 'LongType'
  @@CounterType = 'CounterType'
  @@TimeUUIDType = 'TimeUUIDType'
  def self.UTF8Type
    @@UTF8Type
  end

  def self.LongType
    @@LongType
  end

  def self.CounterType
    @@CounterType
  end

  def self.TimeUUIDType
    @@TimeUUIDType
  end

  # Validation types

  def initialize(options={})
    @heroku = 'api.cassandra.io'
    if (options[:heroku])
      @heroku = options[:heroku];
    end
    @token = options[:token]
    @accountId = options[:accountId]
    @type = 'https'
    if (options[:ssl] == 'false')
      @type = 'http'
    end
    if (options[:port])
      @port = options[:port]
    else
      if (@type == 'http')
        @port = 80
      else
        @port = 443
      end
    end
    if (options[:port])
      @port = options[:port]
    end
    @prefix = @heroku + '/1'
    @apiUrl = @type + '://' + @prefix
  end

  def get_url_builder(getParams)
    appendedUrl = ''
    if (getParams.nil? or getParams == 0)
      # Null or empty item
    else
      count = 0
      getParams.each_pair do |key,value|
        if (count == 0)
          appendedUrl = appendedUrl + '?' + key + '=' + value
          count = count + 1
        else
          appendedUrl = appendedUrl + '&' + key + '=' + value
        end
      end
    end
    return appendedUrl
  end

  def custom_request(options={})
    url = URI.parse(options[:url])
    case options[:method]
    when 'GET'
      @request = Net::HTTP::Get.new(url.to_s)
    when 'POST'
      @request = Net::HTTP::Post.new(url.to_s)
      if (options[:postParams]['body'])
        # Ignore setting form data
      else
        # Handle POSTs
        if (!options[:postParams].empty? && !options[:postParams].nil? && options[:postParams] != 0)
          @request.set_form_data(options[:postParams], sep = '&')
        end
      end
    when 'DELETE'
      @request = Net::HTTP::Delete.new(url.to_s)
      if (options[:postParams])
        @request.set_form_data(options[:postParams], sep = '&')
      end
    end
    
    puts options[:url]

    # Handle basic auth and configuration
    @request.basic_auth @token, @accountId
    sock = Net::HTTP.new(url.host, @port)

    if (@type == 'https')
      sock.use_ssl = true
      sock.ssl_version='SSLv3'
    end

    sock.start do |http|
      # Handle body POSTs
      if (options[:method] == 'POST' || options[:method] == 'DELETE')
        if (!options[:postParams].empty? && !options[:postParams].nil? && options[:postParams] != 0 && !options[:postParams]['body'].nil?)
          return http.request(@request, options[:postParams]['body']).body
        end
      end
      return http.request(@request).body
    end
  end

  # POST  /v0.1/setup
  def setup()
    return self.request('POST', '/setup', nil, [nil, {}])
  end

  # POST  /v0.1/setupaccount
  def setupaccount()
    postParams = {
    }
    return self.request('POST', '/setupaccount', nil, [nil, postParams])
  end

  # POST  /v0.1/keyspace/{kName}
  def create_keyspace(kName)
    return self.request('POST', '/keyspace', nil, [[kName], {}])
  end

  # DELETE  /v0.1/keyspace/{kName}
  def delete_keyspace(kName)
    return self.request('DELETE', '/keyspace', nil, [[kName], {}])
  end

  # GET  /v0.1/columnfamily/{kName}/?
  def get_columnfamilies(kName)
    return self.request('GET', '/columnfamily', {}, [[kName], {}])
  end

  # GET  /v0.1/columnfamily/{kName}/{cfName}/?
  def get_columnfamily(kName, cfName)
    return self.request('GET', '/columnfamily', nil, [[kName, cfName], {}])
  end

  # POST  /v0.1/columnfamily/{kName}/{cfName}/{cType}/?
  def create_columnfamily(kName, cfName, cType)
    return self.request('POST', '/columnfamily', nil, [[kName, cfName, cType], {}])
  end

  # DELETE  /v0.1/columnfamily/{kName}/{cfName}/?
  def delete_columnfamily(kName, cfName)
    return self.request('DELETE', '/columnfamily', nil, [[kName, cfName], {}])
  end

  # POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/?
  def create_column(kName, cfName, cName, cType)
    return self.request('POST', '/column', nil, [[kName, cfName, cName, cType], {}])
  end

  # POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/? >isIndexed=true
  def create_indexed_column(kName, cfName, cName, cType)
    return self.request('POST', '/column', nil, [[kName, cfName, cName, cType], {'isIndexed' => 'true'}])
  end

  # POST  /v0.1/column/{kName}/{cfName}/{cName}/{cType}/? >isIndexed=false
  def remove_indexed_column(kName, cfName, cName, cType)
    return self.request('POST', '/column', nil, [[kName, cfName, cName, cType], {'isIndexed' => 'false'}])
  end

  # POST  /v0.1/data/{kName}/{cfName}/{rowKey}/?
  def post_data(kName, cfName, rowKey, data, optionalParameters = {})
    getParams = {

    }
    if (optionalParameters[:ttl])
      getParams['ttl'] = optionalParameters[:ttl].to_s()
    end
    return self.request('POST', '/data', getParams, [[kName, cfName, rowKey], data])
  end

  # POST  /v0.1/data/{kName}/{cfName}/? >body=<json>
  def bulkpost_data(kName, cfName, data)
    return self.request('POST', '/data', nil, [[kName, cfName], {'body' => data.gsub(/\s+/, '')}])
  end

  # GET  /v0.1/data/{kName}/{cfName}/{rowKey}/?
  def get_data(kName, cfName, rowKey)
    return self.request('GET', '/data', nil, [[kName, cfName, rowKey], {}])
  end

  # GET /v0.1/data/{kName}/{cfName}/{rowKey}?limit=(limit num of results)&fromKey=(key to start range)
  def get_paginatable_data(kName, cfName, rowKey, fromKey, limit)
    return self.request('GET', '/data', {'fromKey' => fromKey, 'limit' => limit.to_s()}, [[kName, cfName, rowKey], {}])
  end

  # DELETE  /v0.1/data/{kName}/{cfName}/{rowKey}/{cName}/?
  def delete_column(kName, cfName, rowKey, cName)
    return self.request('DELETE', '/data', nil, [[kName, cfName, rowKey, cName], {}])
  end

  # DELETE  /v0.1/data/{kName}/{cfName}/{rowKey}/?
  def delete_row(kName, cfName, rowKey)
    return self.request('DELETE', '/data', nil, [[kName, cfName, rowKey], {}])
  end

  # GET  /v0.1/cql/{kName}/{cfName}/?
  def query_cql(kName, cfName, query)
    return self.request('GET', '/cql', {'select' => CGI::escape(query)}, [[kName, cfName], {}])
  end

  # POST /v0.1/counter/{kName}/{cfName}/{rowKey}/?columnName=(your counter column name)&count=(your incremented or decremented value)
  def increment_count(kName, cfName, rowKey, columnName, count)
    return self.request('POST', '/counter', {'count' => count.to_s()}, [[kName, cfName, rowKey, columnName], {}])
  end

  # POST /v0.1/counter/{kName}/{cfName}/{rowKey}/{columnName}?count=(your incremented or decremented value)
  def decrement_count(kName, cfName, rowKey, columnName, count)
    return self.request('POST', '/counter', {'count' => '-' + count.to_s()}, [[kName, cfName, rowKey, columnName], {}])
  end

  # GET /v0.1/counter/{kName}/{cfName}/{rowKey}/{columnName}?count=(your incremented or decremented value)
  def get_count(kName, cfName, rowKey, columnName)
    return self.request('GET', '/counter', nil, [[kName, cfName, rowKey, columnName], {}])
  end

  # Build requests
  def request(method, call, getParams, *params)
    uriStringBuilder = call
    nextParamType = 0
    case method
    when 'GET'
      params.each do |item|
        if (item.nil? or item == 0)
          # Null or empty item
        else
          if (item.class.inspect == 'Array')
            item.each do |arrayItem|
              if (arrayItem.class.inspect == 'Array')
                if (nextParamType.nil? or nextParamType == 0)
                  arrayItem.each do |stringBuilderParam|
                    uriStringBuilder = uriStringBuilder + '/' + stringBuilderParam
                  end
                end
              end
              nextParamType = true
            end
          end
        end
      end
      url = @apiUrl + uriStringBuilder + self.get_url_builder(getParams)
      return custom_request(:url => url, :method => method)
    when 'POST'
      params.each do |item|
        if (item.nil? or item == 0)
          # Null or empty item
        else
          if (item.class.inspect == 'Array')
            item.each do |arrayItem|
              if (arrayItem.class.inspect == 'Array')
                arrayItem.each do |stringBuilderParam|
                  uriStringBuilder = uriStringBuilder + '/' + stringBuilderParam
                end
              end
              if (arrayItem.class.inspect == 'Hash')
                url = @apiUrl + uriStringBuilder + self.get_url_builder(getParams)
                return custom_request(:url => url, :method => method, :postParams => arrayItem)
              end
            end
          end
        end
      end
    when 'DELETE'
      params.each do |item|
        if (item.nil? or item == 0)
          # Null or empty item
        else
          if (item.class.inspect == 'Array')
            item.each do |arrayItem|
              if (arrayItem.class.inspect == 'Array')
                arrayItem.each do |stringBuilderParam|
                  uriStringBuilder = uriStringBuilder + '/' + stringBuilderParam
                end
              end
              if (arrayItem.class.inspect == 'Hash')
                url = @apiUrl + uriStringBuilder + self.get_url_builder(getParams)
                return custom_request(:url => url, :method => method, :postParams => arrayItem)
              end
            end
          end
        end
      end
    end
  end
end