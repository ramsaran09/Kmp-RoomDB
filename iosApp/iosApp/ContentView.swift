import SwiftUI
import Shared


struct AppView: View {
    private var viewModel = HomeViewModel()
    
    @State private var emailId: String = ""

    var body: some View {
        VStack {
            Spacer().frame(height: 16)
            
            TextField("Email", text: $emailId)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal)
            
            Spacer().frame(height: 16)
            
            Button(action: {
                viewModel.onSaveUser()
            
            }) {
                Text("Save")
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
            .padding(.horizontal)
            
            Spacer()
        }
        .onAppear {
            viewModel.getEmail()
        
            print("Email: $viewModel.emailId")
        }
        .padding()
    }
}


struct AppView_Previews: PreviewProvider {
    static var previews: some View {
        AppView()
    }
}
