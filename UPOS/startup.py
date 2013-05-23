#!/usr/bin/env python
# -*- coding: utf8 -*-

import serial

PORT = '/dev/ttyUSB0'

ser = serial.Serial(PORT, 115200,\
                            parity=serial.PARITY_NONE,\
                            stopbits=serial.STOPBITS_ONE,\
                            timeout=0.7,\
                            writeTimeout=0.7)