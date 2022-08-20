//
//  WebView.swift
//  iosApp
//
//  Created by Konstantin Bezzemelnyi on 20.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import WebKit

struct WebView : UIViewRepresentable {
    
    let request: URLRequest
    
    func makeUIView(context: Context) -> WKWebView  {
        return WKWebView()
    }
    
    func updateUIView(_ uiView: WKWebView, context: Context) {
        uiView.load(request)
    }
    
}
