//
//  ViewController.swift
//  MDV348O_Week1
//
//  Created by Natividad Michael Salinas II on 7/5/23.
//

import UIKit


import CoreBluetooth

class ViewController: CBCentralManagerDelegate, CBPeripheralDelegate {
    func isEqual(_ object: Any?) -> Bool {
        <#code#>
    }
    
    var hash: Int = 0
    
    var superclass: AnyClass?
    
    func `self`() -> Self {
        <#code#>
    }
    
    func perform(_ aSelector: Selector!) -> Unmanaged<AnyObject>! {
        <#code#>
    }
    
    func perform(_ aSelector: Selector!, with object: Any!) -> Unmanaged<AnyObject>! {
        <#code#>
    }
    
    func perform(_ aSelector: Selector!, with object1: Any!, with object2: Any!) -> Unmanaged<AnyObject>! {
        <#code#>
    }
    
    func isProxy() -> Bool {
        <#code#>
    }
    
    func isKind(of aClass: AnyClass) -> Bool {
        <#code#>
    }
    
    func isMember(of aClass: AnyClass) -> Bool {
        <#code#>
    }
    
    func conforms(to aProtocol: Protocol) -> Bool {
        <#code#>
    }
    
    func responds(to aSelector: Selector!) -> Bool {
        <#code#>
    }
    
    var description: String = ""
    
    let serviceUUID = CBUUID(string: "06B280C1-419D-4D87-810E-00D88B506717")
    let characteristicUUID = CBUUID(string: "CD570797-087C-4008-B692-7835A1246377")

    var centralManager: CBCentralManager!
    var peripheral: CBPeripheral?
    var characteristic: CBCharacteristic?

    func viewDidLoad() {
        self.viewDidLoad()
        centralManager = CBCentralManager(delegate: self, queue: nil)
    }

    func centralManagerDidUpdateState(_ central: CBCentralManager) {
        if central.state == .poweredOn {
            central.scanForPeripherals(withServices: [serviceUUID], options: nil)
        } else {
            print("Bluetooth is not available.")
        }
    }

    func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral,
                        advertisementData: [String: Any], rssi RSSI: NSNumber) {
        if peripheral.name == "YourPeripheralDeviceName" {
            self.peripheral = peripheral
            central.connect(peripheral, options: nil)
        }
    }

    func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral) {
        peripheral.delegate = self
        peripheral.discoverServices([serviceUUID])
    }

    func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?) {
        if let service = peripheral.services?.first(where: { $0.uuid == serviceUUID }) {
            peripheral.discoverCharacteristics([characteristicUUID], for: service)
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
        if let characteristic = service.characteristics?.first(where: { $0.uuid == characteristicUUID }) {
            self.characteristic = characteristic
            peripheral.setNotifyValue(true, for: characteristic)
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?) {
        if let data = characteristic.value, let value = String(data: data, encoding: .utf8) {
            print("Received value: \(value)")
            // Process the received value here
        }
    }
}


