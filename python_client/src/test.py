# -*- coding: UTF-8 -*-
#!/usr/bin/python

import six
import time
import SubscribeReq_pb2
import SubscribeResp_pb2
from socket import *
import pdb

def create_message(id):
    req = SubscribeReq_pb2.SubscribeReq()
    req.subReqID = id
    req.userName = "Test"
    
    for i in range(0, 100):
        req.productName += "book"

    req.address.append(u'北京市')
    req.address.append(u"上海市")
    req.address.append(u"台北市")
    req.address.append(u"香港")
    
    return req


def DecodeVarint(buffer):
    signbit = 1 << (32 - 1)
    mask = (1 << 32) - 1
    result_type = int
    result = 0
    shift = 0
    pos = 0
    while 1:
      b = six.indexbytes(buffer, pos)
      result |= ((b & 0x7f) << shift)
      pos += 1
      if not (b & 0x80):
        result &= mask
        result = (result ^ signbit) - signbit
        result = result_type(result)
        return result, pos
      shift += 7
      if shift >= 64:
        raise _DecodeError('Too many bytes when decoding varint.')
    return result, pos
   

def send_pkg_header_len(buf):

    value = len(buf)
    bits = value & 0x7f
    value >>= 7

    while value:
        tcpCliSock.send(six.int2byte(0x80 | bits))
        bits = value & 0x7f
        value >>= 7
    llen = six.int2byte(bits)
    print llen
    print len(llen)
    return llen

def send_pkg(buf):

    '''
    * Netty server, there is a pkg length at the begin of payload to avoid mix the packages
    * Fill in pakcage length in client side
    '''
    llen = send_pkg_header_len(buf)

    msg = "%s%s" %(llen, buf)
    tcpCliSock.send(msg)
    #tcpCliSock.send(llen)
    #tcpCliSock.send(buf)
    print msg

    recv_buff=""
    recv_buff = tcpCliSock.recv(1024)
    result, pos = DecodeVarint(recv_buff)
    #pdb.set_trace()
    data_buffer = recv_buff[pos:]
    resp = SubscribeResp_pb2.SubscribeResp()
    resp.ParseFromString(data_buffer)
    print resp


ADDR = ("172.16.196.1",8080)
tcpCliSock = socket(AF_INET,SOCK_STREAM)
tcpCliSock.connect(ADDR)

for i in range(0, 10):
    req = create_message(i)
    buf = req.SerializeToString()
    send_pkg(buf)

tcpCliSock.close()

