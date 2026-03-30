# Networking Concepts Learned

During this session, I learned several important networking concepts that are used in modern computer networks and internet communication.

## Network Address Translation (NAT)
I learned that Network Address Translation (NAT) allows many devices inside a private network to share a single public IP address when accessing the internet. NAT changes the private IP address and port number of outgoing packets into a public IP address and a different port. When the response comes back, NAT translates it back to the original private device.

## How NAT Works
I understood that when a device inside a private network sends data to the internet, the router replaces the private IP address with its own public IP address. It also keeps track of which device sent the request by using port numbers. When the reply returns, the router uses this information to send the data back to the correct device.

## Address Resolution Protocol (ARP)
I learned that ARP is used to find the MAC address of a device when its IP address is known. Since devices communicate on a local network using MAC addresses, ARP helps match an IP address to the correct MAC address before communication can happen.

## TCP/IP
I learned that TCP/IP is the main protocol suite used for communication over networks and the internet. IP is responsible for dividing data into packets and routing them to the destination. TCP ensures that the packets are delivered correctly, in the right order, and without data loss.

## Subnetting
I learned that subnetting is the process of dividing a large network into smaller subnetworks. This helps use IP addresses more efficiently, reduces network traffic, and makes the network easier to manage and secure.