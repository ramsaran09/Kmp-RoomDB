import XCTest
@testable import iosApp // Or your app's module name
import Shared

class HomeViewModelWrapperTests: XCTestCase {

    override func setUpWithError() throws {
        try super.setUpWithError()
        // It's crucial that Koin is initialized before HomeViewModelWrapper tries to use it.
        // If not initialized globally for tests, do it here.
        // This assumes KoinIOSKt.doInitKoin() sets up the *actual* Koin modules.
        // For more isolated tests, you'd set up Koin with test-specific (mocked) modules.
        KoinIOSKt.doInitKoin() 
    }

    func testHomeViewModelWrapperInitialization() {
        let wrapper = HomeViewModelWrapper()
        XCTAssertNotNil(wrapper, "HomeViewModelWrapper should initialize successfully.")
        // Optionally, you could try calling a method if it doesn't have side effects
        // or if Koin is set up with mocks for the underlying KMP ViewModel.
        // For a smoke test, just initialization is often enough.
        wrapper.getEmail() // Example of calling a method
        XCTAssertNotNil(wrapper.email, "Email property should exist")
    }
}
