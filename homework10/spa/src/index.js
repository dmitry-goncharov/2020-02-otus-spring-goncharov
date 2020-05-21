import React from 'react'
import ReactDOM from 'react-dom'
import { Router } from "react-router-dom"
import { createBrowserHistory } from 'history'

import './index.css'

import App from './components/App'

ReactDOM.render((
    <Router history={createBrowserHistory()}>
        <App />
    </Router>
), document.getElementById('root')
)
