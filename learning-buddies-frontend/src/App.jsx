import './App.css'
import { UserProvider } from './contexts/UserContext'
import AppRoutes from './routes/AppRoutes'

function App() {

	return (
		<UserProvider>
			<AppRoutes />
		</UserProvider>
	)
}

export default App
