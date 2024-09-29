package com.example.accessibiltyservice.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AntiScrollService : AccessibilityService() {

    private val TAG = "AntiScrollService"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.packageName == "com.instagram.android") {
            Log.d(TAG, "Accessibility Event Type: ${event.eventType}")
            Log.d(TAG, "Package Name: ${event.packageName}")

            // Capture the current window content
            val rootWindow = rootInActiveWindow
            if (rootWindow != null) {
                // Search for the RecyclerView by ID
                searchRecyclerViewById(rootWindow)

                // Search for RecyclerView in children nodes
                searchAllChildren(rootWindow)
            }
        }
    }

    private fun searchRecyclerViewById(rootNode: AccessibilityNodeInfo) {
        // Attempt to find the RecyclerView by ID
        val nodeInfo = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/recycler_view")
        nodeInfo?.forEach { node ->
            if (node.className == "androidx.recyclerview.widget.RecyclerView") {
                Log.d(TAG, "Found RecyclerView by ID")
                node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
            }
        } ?: Log.d(TAG, "No RecyclerView found by ID.")
    }

    private fun searchAllChildren(node: AccessibilityNodeInfo) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                Log.d(TAG, "Child Class: ${child.className}, Content Description: ${child.contentDescription}")

                // Check if it's a RecyclerView
                if (child.className == "androidx.recyclerview.widget.RecyclerView") {
                    Log.d(TAG, "Found RecyclerView in children")
                    child.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
                }

                // Recursively search in children
                searchAllChildren(child)
            }
        }
    }

    override fun onInterrupt() {
        // Handle any cleanup or interruption logic if needed
        Log.d(TAG, "Accessibility Service Interrupted")
    }
}
