//
//  ProgressView.swift
//  iosApp
//
//  Created by Konstantin Bezzemelnyi on 19.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        ZStack {
            Color
                .blue
                .ignoresSafeArea()
            
            ProgressView()
                .progressViewStyle(.circular)
                .scaleEffect(3)
        }
    }
}
