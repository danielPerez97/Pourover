//
//  ComposeSwiftControllerToSwiftUI.swift
//  Pourover
//
//  Created by Daniel Perez on 4/1/25.
//

import UIKit
import SwiftUI
import FourSixFrontendLib

struct ComposeViewControllerToSwiftUI: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        return createViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        uiViewController.view
    }
}
