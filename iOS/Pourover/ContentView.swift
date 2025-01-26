//
//  ContentView.swift
//  Pourover
//
//  Created by Daniel Perez on 9/23/24.
//

import SwiftUI
import FourSixFrontendLib

@MainActor
class SwiftFourSixPresenter: ObservableObject
{
    @MainActor
    var presenter = createPresenter()
    
    @Published
    private(set) var state: FourSixState? = nil
    
    @MainActor
    func activate() async {
        for await state in presenter.presenter {
            self.state = state
        }
    }
}

struct ContentView: View {
    
    @MainActor
    @ObservedObject
    var presenter = SwiftFourSixPresenter()
    
    var body: some View {
        VStack {
            Text("Grams: #\(presenter.state?.grams)")
            Button("Test") {  }
        }
        .padding()
        .task {
            await presenter.activate()
        }
    }
}

#Preview {
    ContentView()
}
