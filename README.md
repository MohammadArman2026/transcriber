# transcriber
//A Kotlin-based audio transcription app using Clean ArchitectureConverts speech to text in real-time.
// Record and upload audio and get accurate text transcriptions. Built with Kotlin and Jetpack Compose.
//Audio-to-text transcription app with Clean Architecture, Kotlin, Jetpack Compose, and remote API integration.
//Kotlin transcription app | Clean Architecture | Jetpack Compose | Speech-to-Text

#Architecture 
│
├── data
│   ├── repository
│   │   └── TranscriptionRepositoryImpl  # Implements domain repository interface
│   └── remote
│       └── TranscriptionApiService      # Interface for remote API calls
│
├── domain
│   ├── model
│   │   └── TranscriptionResponse        # Represents the transcription result of audio
│   └── repository
│       └── TranscriptionRepository      # Interface defining transcription operations
│
├── ui
│   └── TranscriptionScreen
│       ├── TranscriptionScreen          # The Composable UI screen
│       ├── TranscriptionUiState         # Represents the UI state of the screen
│       └── TranscriptionViewModel       # Manages the state and business logic of the transcription screen
│
└── di
    ├── ApiKey                           # Holds API keys for remote services
    ├── RepositoryModule                 # binds interface with repository implementation
    ├── TranscriptionClass               # Helper/provider for transcription dependencies
    └── TranscriptionModule              # Provides DI for transcription components
