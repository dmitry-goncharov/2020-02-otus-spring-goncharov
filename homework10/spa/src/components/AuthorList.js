import React, { Fragment, PureComponent } from 'react'
import { Link } from "react-router-dom"
import { page, api } from './AppContext'

export default class AuthorList extends PureComponent {
    constructor() {
        super()
        this.state = { authors: [] }
    }

    componentDidMount() {
        this.fetchAuthors()
    }

    fetchAuthors() {
        fetch(api.author)
            .then((response) => response.json())
            .then((authors) => this.setState({ authors }))
            .catch((error) => console.error(error))
    }

    deleteAuthor(authorId) {
        fetch(api.author + "/" + authorId, { method: 'DELETE' })
            .then((response) => {
                if (response.ok) {
                    this.setState({ authors: this.state.authors.filter(author => author.id !== authorId) })
                } else {
                    response.json().then((error) => {
                        const details = error.details ? error.details.join(', ') : ""
                        alert("Error: " + error.message + "\nDetails: " + details)
                    })
                }
            })
    }

    getEditPageWithParams(authorId) {
        return page.author.edit + '/' + authorId
    }

    getBookPageWithParams(authorId) {
        return page.book.root + "?authorId=" + authorId
    }

    render() {
        return (
            <Fragment>
                <h2>List of authors</h2>
                <div className="block">
                    <Link to={page.author.add}>Add author</Link>
                </div>
                {this.state.authors.length > 0 ?
                    <div className="block">
                        <table className="block-content">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.authors.map((author) => (
                                    <tr key={author.id}>
                                        <td>{author.id}</td>
                                        <td>{author.name}</td>
                                        <td>
                                            <Link to={this.getEditPageWithParams(author.id)}>Edit</Link>
                                            <span> | </span>
                                            <a id="delete" href="#delete" onClick={() => { this.deleteAuthor(author.id) }}>Delete</a>
                                            <span> | </span>
                                            <Link to={this.getBookPageWithParams(author.id)}>Books</Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    : <div className="block">No authors</div>}
            </Fragment>
        )
    }
}
