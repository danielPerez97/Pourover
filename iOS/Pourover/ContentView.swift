//
//  ContentView.swift
//  Pourover
//
//  Created by Daniel Perez on 9/23/24.
//

import SwiftUI
//import FourSixCoreLib
//import FourSixPresenterLib
import FourSixFrontendLib

// PICKUP: DisplayLinkClock, from Molecule, is being weird. 

let presenter = createPresenter()

struct ComposeViewController: UIViewControllerRepresentable {
    @State
    var state: FourSixState = presenter.models.value!
    
    func makeUIViewController(context: Context) -> UIViewController {
        return createViewController(state: state, presenter: presenter)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    @State
    var state: FourSixState = presenter.models.value!
    
    var body: some View {
        VStack {
            Text("Grams: #\(state.grams)")
            Button("Test") {  }
        }
        .padding()
        .task {
            await observeModels()
        }
    }
    
    func observeModels() async {
        // Observe the presenter.models asynchronously
        for await model in presenter.models {
            // Update the state on the main thread
            await MainActor.run {
                state = model!
            }
        }
    }
}

#Preview {
    ContentView()
}
