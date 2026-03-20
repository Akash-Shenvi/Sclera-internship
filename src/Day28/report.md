## Networking Concepts Overview

This report summarizes key networking concepts studied, including EtherTypes, MAC addressing, and Virtual Local Area Networks (VLANs).

---

### 5.1 EtherTypes

An **EtherType** is a 2-byte field in an Ethernet frame that indicates which protocol is encapsulated in the payload. It enables network devices to correctly interpret and process incoming data.

**Common EtherTypes:**
- `0x0800` — IPv4
- `0x86DD` — IPv6
- `0x8100` — VLAN tagging (IEEE 802.1Q)

---

### 5.2 MAC Addressing

A **MAC (Media Access Control) address** is a unique 48-bit identifier assigned to a network interface for communication at the Data Link Layer.

**Types of MAC addresses:**
- **Unicast:** Identifies a single device
- **Multicast:** Identifies a group of devices
- **Broadcast:** Targets all devices within the same network

MAC addresses are typically represented in hexadecimal format:
`00:1A:2B:3C:4D:5E`

---

### 5.3 Virtual Local Area Networks (VLANs)

**VLANs (Virtual Local Area Networks)** are used to logically segment a physical network into multiple broadcast domains, improving network organization and security.

- Each VLAN is assigned a unique **VLAN ID (VID)**
- VLAN tagging follows the **IEEE 802.1Q** standard
- Tagged frames use the EtherType `0x8100` to indicate VLAN information

This mechanism allows multiple VLANs to operate over the same physical network infrastructure efficiently.

---