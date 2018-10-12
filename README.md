[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[ ![Download](https://api.bintray.com/packages/benayounp/Badass/BADASS/images/download.svg) ](https://bintray.com/benayounp/Badass/BADASS/_latestVersion)

# BADASS
BADASS (Benayoun's Asynchronous Data Android Simple System) fetch fresh remote data in the background to give it instantly to user, even offline!

## Goals

### The problem
Managing remote data can be a pain in the ass in an Android APP. Whether this data is in a web server, a connected object and ironically in some local API you have to connect to data provider, manage the eventual disconnections and the provider own lifecycle. 

### The solution
The goals of Badass is to work in the background to fetch data and save them as displayable as possible for user.

##  Features
* Usable on Android API version 14 or newer
* Java with no external libraries (use only appcompat-v7)
* One Backround thread system that handles ALL data requests
* Handle Android system events (Internet connection, Activity Lifecycle, Screen state)
* Utilities to help you manage Storage, Permissions, Logs. 

## Getting Started
Just add this line in your module build.gradle dependencies

```java
dependencies {
    implementation 'com.github.benayounp:badass:0.6.041'
}
```
## Samples
<img src="https://lh3.googleusercontent.com/LdrMoHsKsoCYWeQZmzptl5WS9UnW4i2UbpOASGoA0N2g9dv8tgHxsjCHw-IWtWsUbw=s180-rw" align="left" width="50" hspace="10" vspace="10">
BadassWeather is a simple weatherApp using Badass framework. You can find it <a href="https://github.com/BenayounP/BadassWeather">here</a>.
</br></br>

## Introduction to Framework
You can learn more about the framework with a bunch of medium posts that starts <a href="https://medium.com/p/d45c5b0f0304/edit">here</a>. 

## Contributing
if you want to help in any way, just send me an [email](mailto:pierre<àcabnum.fr)

## License
This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details
