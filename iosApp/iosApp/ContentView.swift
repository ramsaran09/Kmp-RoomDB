import SwiftUI
import Shared
import KMPNativeCoroutinesAsync // Import for StateFlow observation

// Wrapper class to make HomeViewModel usable with @StateObject/@ObservedObject
class HomeViewModelWrapper: ObservableObject {
    // The Koin-managed instance of HomeViewModel
    // Accessed via the ViewModelProvider object defined in KoinIOS.kt
    // Note: Kotlin's `object ViewModelProvider` is accessed as `ViewModelProvider.shared` in Swift.
    private let koinViewModel: HomeViewModel = ViewModelProvider.shared.homeViewModel

    // Published property to mirror HomeViewModel's emailId StateFlow
    @Published var email: String = ""
    
    // Cancellable for observing the StateFlow
    private var cancellable: Any?

    init() {
        // Initial fetch of email
        koinViewModel.getEmail()
        
        // Observe the emailId StateFlow from HomeViewModel
        // Using KMPNativeCoroutinesAsync to bridge Kotlin's Flow to Combine's Publisher
        // The `koinViewModel.emailId` is a StateFlow<String>
        // `createPublisher(for: koinViewModel.emailId)` converts it to a Combine Publisher
        cancellable = createPublisher(for: koinViewModel.emailId)
            .receive(on: DispatchQueue.main) // Ensure UI updates are on the main thread
            .sink(receiveCompletion: { completion in
                if case let .failure(error) = completion {
                    print("Error observing emailId: \(error)")
                }
            }, receiveValue: { [weak self] newEmail in
                self?.email = newEmail
            })
    }

    // Expose methods from HomeViewModel
    func onSaveUser() {
        koinViewModel.onSaveUser()
    }

    func getEmail() {
        koinViewModel.getEmail() // This will trigger the StateFlow to update, and the sink will update @Published var
    }
    
    // Method to update the email in the ViewModel
    // This corresponds to onEmailIdChange in the Kotlin ViewModel
    func onEmailChange(newEmail: String) {
        koinViewModel.onEmailIdChange(emailId: newEmail)
    }
    
    deinit {
        cancellable?.cancel()
    }
}

struct AppView: View {
    // Use @StateObject for iOS 14+ to own the HomeViewModelWrapper lifecycle
    @StateObject private var wrapper = HomeViewModelWrapper()
    
    var body: some View {
        VStack {
            Spacer().frame(height: 16)
            
            // TextField binds to the wrapper's @Published email property
            // And calls onEmailChange when the text changes
            TextField("Email", text: Binding(
                get: { wrapper.email },
                set: { newEmail in
                    wrapper.email = newEmail // Update local published property
                    wrapper.onEmailChange(newEmail: newEmail) // Propagate change to KMP ViewModel
                }
            ))
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .padding(.horizontal)
            
            Spacer().frame(height: 16)
            
            Button(action: {
                wrapper.onSaveUser()
            }) {
                Text("Save")
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
            .padding(.horizontal)
            
            // Button to explicitly refresh email (demonstrates getEmail)
            Button(action: {
                wrapper.getEmail()
            }) {
                Text("Refresh Email from DB")
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding()
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
            .padding(.horizontal)
            
            Spacer()
        }
        .onAppear {
            // Initial data load is handled by the wrapper's init
            // wrapper.getEmail() // Can be called here if not in init, but init is better for initial setup
        }
        .padding()
    }
}

struct AppView_Previews: PreviewProvider {
    static var previews: some View {
        AppView()
    }
}
