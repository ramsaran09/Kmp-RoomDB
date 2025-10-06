import SwiftUI
import Shared // Import the shared module

@main
struct iOSApp: App {
    init() {
        // Call the initKoin function from the shared module
        // The KoinIOS.kt file contains the top-level function `initKoin()`
        // which is exposed to Swift as `KoinIOSKt.doInitKoin()`
        KoinIOSKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            AppView()
        }
    }
}
