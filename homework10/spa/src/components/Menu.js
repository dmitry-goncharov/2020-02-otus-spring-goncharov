import React, { Component } from 'react'
import { Link } from "react-router-dom"
import { page } from './AppContext'

export default class Menu extends Component {
    render() {
        const pages = [
            { href: page.genre.root, name: 'Genres' },
            { href: page.author.root, name: 'Authors' },
            { href: page.book.root, name: 'Books' }
        ]

        return (
            <h1>
                {pages.map(({ href, name }, index) => (
                    <span key={href}>
                        <Link to={href}>{name}</Link>
                        {index < pages.length - 1 ? " | " : ""}
                    </span>
                ))}
            </h1>
        )
    }
}
