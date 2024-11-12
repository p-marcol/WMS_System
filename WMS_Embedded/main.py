import machine
import time
import network
import socket
import requests
import os
import json
import ubinascii
from machine import Pin, reset
from time import sleep
from utime import sleep, sleep_ms
from mfrc522 import MFRC522

led_R = Pin(18, Pin.OUT)
led_G = Pin(17, Pin.OUT)
led_B = Pin(16, Pin.OUT)
reader = MFRC522(spi_id=0,sck=6,miso=4,mosi=7,cs=5,rst=22)

ssid = ''
password = ''
server = ''
port = ''

def qs_parse(qs):
    parameters = {}
    ampersandSplit = qs.split("&")
    for element in ampersandSplit:
        equalSplit = element.split("=")
        parameters[equalSplit[0]] = equalSplit[1]
    return parameters

def openConfigPage():
    setRGB(0,1,0)
    ap_ssid = "WMS-AP"
    ap_pwd = "123456789"
    ap = network.WLAN(network.AP_IF)
    ap.config(essid=ap_ssid, password=ap_pwd)
    ap.active(True)
    ap_addr = ap.ifconfig()[0]
    print(ap_addr)
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('', 80))
    s.listen(5)
    with open("configPage.html", 'r') as file:
        page = ''
        line = file.readline()
        while line:
            page += line
            line = file.readline()
    while True:
        conn, addr = s.accept()
        request = conn.recv(1024)
        try:
            request = str(request).split()[1]
            request = request.split("?")
            if request[0] == '/save':
                qs = qs_parse(request[1])
                json_obj = json.dumps(qs)
                with open("config.json", "w") as outfile:
                    outfile.write(json_obj)
                reset()
        except IndexError:
            pass
        response = page.format(ssid, password, server, port)
        conn.send(response)
        conn.close()

def uidToString(v, sep):
    s=""
    for i in v:
        if i != v[0]:
            s = s+ sep
        s = s + "{:02X}".format(i)
    return s

def setRGB(R, G, B):
    led_R.value(R)
    led_G.value(G)
    led_B.value(B)

def load_config_default(file):
    try:
        with open(file) as fd:
            return json.load(fd)
    except OSError:
        openConfigPage()

def connect_to_wifi():
    max_wait = 100
    sta_if = network.WLAN(network.STA_IF)
    if not sta_if.isconnected():
        sta_if.active(True)
        sta_if.connect(ssid, password)
        while max_wait > 0:
            if sta_if.status() < 0 or sta_if.status() >=3:
                print(str(sta_if.status()))
                break
            max_wait -= 1
            time.sleep(1)
    if sta_if.status() != 3:
        return False
    else:
        print("Connected to IP: ", sta_if.ifconfig()[0])
        return sta_if.ifconfig()[0]
    
def send_uid(uid):
    setRGB(1, 1, 0)
    try:
        res = requests.get(f"http://{server}:{port}/access/{symbol}/{uid}")
    except:
        return False
    if res.text == "true":
        setRGB(0, 1, 0)
    else:
        setRGB(1, 0, 0)
    sleep(1.5)
    setRGB(0, 0, 1)

def handshake():
    mac_address = ubinascii.hexlify(network.WLAN().config('mac'),':').decode()
    try:
        res = requests.get(f"http://{server}:{port}/connection/handshake/{mac_address}")
        print(res.text)
        return res.text
    except:
        return False
    
def network_error():
    setRGB(1,1,0)
    openConfigPage()

def handshake_error():
    max_wait = 10
    server_connection = False
    setRGB(1,0,1)
    while max_wait > 10:
        print(f"Waiting: {max_wait}")
        sleep(2)
        server_connection = handshake()
        max_wait -= 1
    if not server_connection:
        openConfigPage()
    return server_connection

####################################################################################
#                CONFIG
####################################################################################
setRGB(1,1,0)
    
config = load_config_default("config.json")

ssid = config.get("ssid")
password = config.get("password")
server = config.get("server")
port = config.get("port")

previous_uid = ""

ip = connect_to_wifi()

if not ip:
    network_error()

symbol = handshake()

if not symbol:
    handshake_error()

setRGB(0,0,1)

####################################################################################

try:
    while True:
        reader.init()
        (stat, tag_type) = reader.request(reader.REQIDL)
        if stat == reader.OK:
            (stat, uid) = reader.SelectTagSN()
            if uid == PreviousCard:
                continue
            if stat == reader.OK:
                uid_string = uidToString(uid, "")
                print(uid_string)
                send_uid(uid_string)
                PreviousCard = uid
            else:
                pass
        else:
            PreviousCard=[0]
        sleep_ms(100)
except KeyboardInterrupt:
    pass
