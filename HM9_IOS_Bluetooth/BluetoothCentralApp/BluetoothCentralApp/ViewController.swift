//
//  ViewController.swift
//  BluetoothCentralApp
//
//  Created by Joseph Sheckels for Full Sail University
//

//--1 For this demonstration we're creating a Central side object in a Bluetooth Low Energy connection. The purpose of this demo is to show you the workings of my app that you'll be using to find and connect to your periperal side in the Bluetooth CE. You will notice too that this app was written as an OSX application and not an iOS one. This is because the simulator can not access the Bluetooth radio on your Macbook for testing. You can only do that on real hardware such as your iPad.

//1.1 - Before we do ANY work on the code don't forget to set the permission in your Info.plist
//      Privacy - Bluetooth Always Usage Description and set a string for the user to tell them why you need permission.
//      This should happen on iOS as well.


//  To start we'll import the CoreBluetooth framework into our file. Be sure to add the CoreBluetooth.framework to your project before attempting to use any of the CB prefix classes.
//  We'll also import the AVFoundation framework to play our audio
import Cocoa
import CoreBluetooth
import AVFoundation

//--2 We need our class to be a CBCentralManagerDelegate in order to find and make the connection with a peripheral device and the CBPeripheralDelegate to recieve callbacks from a connected peripheral such and responses to requests for the peripheral's services, characteristics, or when the peripheral updates a characteristic that this central is subscribed to.
class ViewController: NSViewController, CBCentralManagerDelegate, CBPeripheralDelegate {
    
    //Test UUIDs for finding specific services and characteristics. We typically use these in order to prevent methods like didDiscover from returning every single Bluetooth device in range of ours or to find various devices that match specific data types we're looking for.
    let soundboardServiceUUID = CBUUID(string: "06B280C1-419D-4D87-810E-00D88B506717")
    let soundboardCharacteristicUUID = CBUUID(string: "CD570797-087C-4008-B692-7835A1246377")
    
    var soundArray:[AVAudioPlayer] = [AVAudioPlayer]()

    //The CBCentralManager is the main member we'll use for this class to find and communicate with peripheral objects. If we were a peripheral we would instead use a CBPeripheralManager.
    var centralManager:CBCentralManager!
    
    //An instance of a peripheral object. We hang on to it once we connect so we can utilize it's data whereever we need to.
    var discoveredPeripheral:CBPeripheral!
    
    //Elements for updating the UI
    @IBOutlet var dukeImage:NSImageView!
    @IBOutlet var textView:NSTextView!
  

    override func viewDidLoad() {
        super.viewDidLoad()
        //Start with image hidden
        self.dukeImage.isHidden = true;
        
        //Set the background color to black
        self.view.layer?.backgroundColor = NSColor.black.cgColor;
        
        self.textView.backgroundColor = NSColor.white;
        
        //Loop through all of our sounds effects and load them into separate AVAudioPlayers
        //This allows for multiple effects to play overtop of each other
        for i in 1...6
        {
            
            let soundPath:String = Bundle.main.path(forResource: "\(i)", ofType: "mp3")!
            let soundUrl:URL = URL.init(fileURLWithPath: soundPath)
            do{
                // Create audio player object and initialize with URL to sound
                let newAVPlayer:AVAudioPlayer = try AVAudioPlayer.init(contentsOf: soundUrl)
                soundArray.append(newAVPlayer)
            }
            catch{
                //Something went wrong! Kill it and display error
                 fatalError("Audio load error")
            }
            
        }
        
        self.appendToMyTextView(newText: "Application started")
        
        //Initialize our central manager and set the delegate. We set the queue to nil in order to use the default queue but this can be subsituted for your own queue based on the application's needs.
        centralManager = CBCentralManager(delegate: self, queue: nil)
    }
    
    //--3 This method gets called automatically once we initialize our central manager. It helps us determine the current state of our Bluetooth hardware
    func centralManagerDidUpdateState(_ central: CBCentralManager) {
        
        //If we're in any state but poweredOn we can't do much in here. Certain states such as poweredOff are a good oportunity to display a notification to the end user that they need to turn Bluetooth on for their device or at least to let them know there is some sort of error.
        if (centralManager.state != .poweredOn)
        {
            //Anything other than powered on is a problem
            self.appendToMyTextView(newText: "Issue with central manager state. State is \(centralManager.state.rawValue)")
            return;
        }
        
        //Otherwise, the state is valid and we can start scanning
        if (centralManager.state == .poweredOn)
        {
            //We'll call scanForPeripherals which will find any Bluetooth device in range that meets our specifications. You can pass nil to the services array here to find all Bluetooth devices in range
            centralManager.scanForPeripherals(withServices: [soundboardServiceUUID], options: nil)
            //Update UI
            self.appendToMyTextView(newText: "Scanning started with service UUID \(soundboardServiceUUID)")
        }
    }
    
    //--4 This method will return for each device found by scanForPeripherals
    func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral, advertisementData: [String : Any], rssi RSSI: NSNumber)
    {
        //Check state, if disconnected we want to connect since it matches our UUID we scanned for
        if (peripheral.state == .disconnected)
        {
            //Update UI, the ?? is to provide a default value in case name is empty
            self.appendToMyTextView(newText: "Discovered \(peripheral.name ?? "a device ") at \(RSSI) signal strength.")
            
            self.appendToMyTextView(newText: "Stopping scan and connecting to peripheral \(peripheral.name ?? "a device.")")
            
            //Hold on to a copy of the peripheral object in case we need it later
            discoveredPeripheral = peripheral;
            
            //Stop scanning first. We do this for two reasons, one we don't want to keep scanning while we're trying to connect as it's taxing on the system to do both. The other is how much battery the scanning process can utilize if just left on. I have left this commented out for now so you can see all the local devices print out to the console.
            centralManager.stopScan()
            
            // And connect
            centralManager.connect(peripheral, options: nil)
        }
    }
    
    //If we fail to connect update the visible text for the user. Also you can start scanning again here which will take us back to didDiscover.

    func centralManager(_ central: CBCentralManager, didFailToConnect peripheral: CBPeripheral, error: Error?)
    {
        self.appendToMyTextView(newText: "Failed to connect to \(peripheral.name ?? "a device.")")
        
        //Update UI
        self.appendToMyTextView(newText: "Restarting scan for service UUID \(soundboardServiceUUID)")
        
        // Scan for devices
        centralManager.scanForPeripherals(withServices: [soundboardServiceUUID], options: nil)
    }
    
    //--5 This method will fire if the connection was successful.
    func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral)
    {
        self.appendToMyTextView(newText: "Success! Connected to \(peripheral.name ?? "a device.")")
        
        //Set us to the PeripheralDelegate so we can intercept calls from the discoveredPeripheral object
        discoveredPeripheral.delegate = self;
        
        //We could pass an array of service UUIDs so we can get the associated CBService objects from the peripheral. If you want all of the services, as we do for this demonstration, pass nil to the discoverServices method instead of this array.
        discoveredPeripheral.discoverServices(nil)
    }
    
    //--6 this method will return any matching services from our discoverServices call. You'll note that this does not pass us back any services directly. Instead it will populate the services array of our peripheral device with any matching services from our discover call.
    func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?)
    {
        //If there's an error exit this method. Also a good place to diconnect and start scanning
        if ((error) != nil)
        {
            //Update UI
            self.appendToMyTextView(newText: "Failed to find services for \(peripheral.name ?? "connected device"). Error \(error.debugDescription)")
            return;
        }
    
        //If we loop through the services in our peripheral object we can then ask for the characteristics of each service. Here in this example we let every single service return all of their characteristics. Typically this is not nessesary. Instead you can match the CBService objects UUID to any that you need the characteristics for and you can further narrow down any results by passing in an array of UUID objects for specific characteristics.
        for service in peripheral.services!{
            peripheral.discoverCharacteristics(nil, for: service)
        }
    }
    
    //--7 this method will return any matching characteristics from our discoverCharacteristics call. You'll note that this does not pass us back any characteristics directly. Instead it will populate the characteristics array of the returned service with any matching characteristics from our discover call.
    func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?)
    {
        if (error != nil)
        {
            //Update UI
            self.appendToMyTextView(newText: "Failed to find services for \(peripheral.name ?? "connected device"). Error \(error.debugDescription)")
            return;
        }
        
        //If we loop through the characteristics in our service object we can then make any calls nessesary to interact with those characteristics. We match UUIDs using isEqual to look for specific data we wish to interact with. We can call any characteristic write, read, or subscribe requests.
        for characteristic in service.characteristics!
        {
            if(characteristic.uuid.isEqual(soundboardCharacteristicUUID))
            {
                //Here we're subscribing to the characteristic. If the characteristic is properly set up this means that any time the value for that characteristic is updated we'll get a call back with the new value in didWriteValueFor, as below.
                peripheral.setNotifyValue(true, for: characteristic)
            }
        }
    }
    
    func peripheral(_ peripheral: CBPeripheral, didUpdateNotificationStateFor characteristic: CBCharacteristic, error: Error?)
    {
        //If for whatever reason we somehow got mismatched UUIDs. Shouldn't happen since there's only 1 characteristic
        if (!characteristic.uuid.isEqual(soundboardCharacteristicUUID))
        {
            return;
        }
        
        //Just to let the user know that we successfully subscribed to the characteristic and it is set up properly
        if (characteristic.isNotifying)
        {
            //Update UI
            self.appendToMyTextView(newText: "Notification began on: \(characteristic)")
        }
    }
    
    //--8 This method will call any time the peripheral updates a characteristic's value that this central is subscribed to.
    func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?)
    {
        if (error != nil) {
            self.appendToMyTextView(newText: "Failed to get new characteristic value for \(peripheral.name ?? "connected device"). Error \(error.debugDescription)")
            return;
        }
        
        //In this example, we're assuming we've been sent back a value that was a string with UTF8 encoding. What is sent back in the value parameter is simply a Data object though and can be any kind of data so it's important that you ensure you're using the correct data types.
        let stringFromData = String.init(data: characteristic.value!, encoding: .utf8)
        let indexValue:Int = (Int(stringFromData!) ?? 1) - 1
        let player:AVAudioPlayer = soundArray[indexValue];
        player.play();
        
        //Show Image
        dukeImage.isHidden = false
    }
    
    func centralManager(_ central: CBCentralManager, didDisconnectPeripheral peripheral: CBPeripheral, error: Error?)
    {
        self.appendToMyTextView(newText: "Peripheral disconnected")
        
        if (discoveredPeripheral != nil)
        {
            centralManager.cancelPeripheralConnection(discoveredPeripheral)
            discoveredPeripheral = nil
        }
        
        dukeImage.isHidden = true
        
        //Update UI
        self.appendToMyTextView(newText: "Restarting scan for service UUID \(soundboardServiceUUID)")
        
        // Scan for devices
        centralManager.scanForPeripherals(withServices: [soundboardServiceUUID], options: nil)
    }
    
    //Update the bottom text output view with messages to help keep track of the application status.
    func appendToMyTextView(newText: String)
    {
        DispatchQueue.main.async {
            let attr: NSAttributedString = NSAttributedString.init(string: "\(newText)\n")
            self.textView.textStorage?.append(attr)
            //Auto scroll the view to keep the latest information visible.
            self.textView.scrollRangeToVisible(NSMakeRange(self.textView.string.count, 0))
        }
    }

    //This is part of MacOS apps and can be safely ignored for our purposes but leave it intact here.
    override var representedObject: Any? {
        didSet {
        // Update the view, if already loaded.
        }
    }


}

