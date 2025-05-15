//
//  ContentView.swift
//  Pourover
//
//  Created by Daniel Perez on 9/23/24.
//

import SwiftUI
import FourSixFrontendLib

//@MainActor
//class SwiftFourSixPresenter: ObservableObject
//{
//    @MainActor
//    var presenter = createPresenter()
//    
//    @Published
//    private(set) var state: FourSixState? = nil
//    
//    @MainActor
//    func activate() async {
//        Task { @MainActor in
//            for await newState in presenter.presenter {
//                print("State: \(newState)")
//                self.state = newState
//            }
//        }
//    }
//    
//    func sink(event: FourSixEvent) {
//        print("Event: \(event)")
//
//        presenter.take(event: event)
//    }
//}

struct ContentView: View {
    
//    @MainActor
//    @ObservedObject
//    var presenter = SwiftFourSixPresenter()
//    
    var body: some View {
        ZStack {
//            if let state = presenter.state {
//                ComposeViewControllerToSwiftUI()
//            } else {
//                // Show a loading view or placeholder
//                Text("Loading...")
//            }
            ComposeViewControllerToSwiftUI()
                .ignoresSafeArea(.keyboard)
        }
        .task {
//            await presenter.activate()
        }
    }
}

#Preview {
    ContentView()
}
