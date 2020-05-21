import React, { Fragment, PureComponent } from 'react'
import { Link } from "react-router-dom"
import { page, api } from './AppContext'

export default class BookList extends PureComponent {
    constructor() {
        super()
        this.state = { books: [], genre: null, author: null }
    }

    componentDidMount() {
        this.fetchBooks()
        const params = new URLSearchParams(this.props.location.search)
        if (params.get('genreId')) {
            this.fetchGenre(params.get('genreId'))
        }
        if (params.get('authorId')) {
            this.fetchAuthor(params.get('authorId'))
        }
    }

    fetchBooks() {
        fetch(api.book + this.props.location.search)
            .then((response) => response.json())
            .then((books) => this.setState({ books }))
            .catch((error) => console.error(error))
    }

    fetchGenre(genreId) {
        fetch(api.genre + '/' + genreId)
            .then((response) => response.json())
            .then((genre) => this.setState({ genre }))
            .catch((error) => console.error(error))
    }

    fetchAuthor(authorId) {
        fetch(api.author + '/' + authorId)
            .then((response) => response.json())
            .then((author) => this.setState({ author }))
            .catch((error) => console.error(error))
    }

    deleteBook(bookId) {
        fetch(api.book + "/" + bookId, { method: 'DELETE' })
            .then((response) => {
                if (response.ok) {
                    this.setState({ books: this.state.books.filter(book => book.id !== bookId) })
                } else {
                    response.json().then((error) => {
                        const details = error.details ? error.details.join(', ') : ""
                        alert("Error: " + error.message + "\nDetails: " + details)
                    })
                }
            })
    }

    getAddPageWithParams() {
        return this.props.location.search.length > 0
            ? page.book.add + this.props.location.search
            : page.book.add
    }

    getEditPageWithParams(bookId) {
        return this.props.location.search.length > 0
            ? page.book.edit + '/' + bookId + this.props.location.search
            : page.book.edit + '/' + bookId
    }

    getCommentPageWithParams(bookId) {
        return this.props.location.search.length > 0
            ? page.comment.root + this.props.location.search + '&bookId=' + bookId
            : page.comment.root + '?bookId=' + bookId
    }

    render() {
        return (
            <Fragment>
                <h2>
                    List of books
                    {this.state.genre && <span> by genre "{this.state.genre.name}"</span>}
                    {this.state.author && <span> by author "{this.state.author.name}"</span>}
                </h2>
                <div className="block">
                    <Link to={this.getAddPageWithParams()}>Add book</Link>
                </div>
                {this.state.books.length > 0 ?
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
                                {this.state.books.map((book) => (
                                    <tr key={book.id}>
                                        <td>{book.id}</td>
                                        <td>{book.name}</td>
                                        <td>
                                            <Link to={this.getEditPageWithParams(book.id)}>Edit</Link>
                                            <span> | </span>
                                            <a id="delete" href="#delete" onClick={() => { this.deleteBook(book.id) }}>Delete</a>
                                            <span> | </span>
                                            <Link to={this.getCommentPageWithParams(book.id)}>Comments</Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    : <div className="block">No books</div>}
            </Fragment>
        )
    }
}
