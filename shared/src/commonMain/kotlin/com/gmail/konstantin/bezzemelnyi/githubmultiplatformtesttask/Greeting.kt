package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}