import React, { Fragment, PureComponent } from 'react'
import { page, api } from './AppContext'

export default class CommentEdit extends PureComponent {
    constructor() {
        super()
        this.book = 'book'
        this.name = 'name'
        this.text = 'text'
        this.state = { id: '', bookId: '', name: '', text: '', books: [], shouldFetchBooks: true }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        this.fetchComment()
        const params = new URLSearchParams(this.props.location.search)
        const bookId = params.get('bookId')
        if (bookId) {
            this.setState({ bookId: bookId, shouldFetchBooks: false })
        } else {
            this.fetchBooks()
        }
    }

    fetchComment() {
        fetch(api.comment + '/' + this.props.match.params.id)
            .then((response) => response.json())
            .then((comment) => this.setState({ id: comment.id, bookId: comment.bookId, name: comment.name, text: comment.text }))
            .catch((error) => console.error(error))
    }

    fetchBooks() {
        fetch(api.book)
            .then((response) => response.json())
            .then((books) => {
                if (books.length > 0) {
                    this.setState({ bookId: books[0].id, books: books })
                }
            })
            .catch((error) => console.error(error))
    }

    handleChange(event) {
        switch (event.target.name) {
            case this.book:
                this.setState({ bookId: event.target.value })
                break
            case this.name:
                this.setState({ name: event.target.value })
                break
            case this.text:
                this.setState({ text: event.target.value })
                break
            default:
                console.warn("Incorrect name=" + event.target.name)
        }
    }

    handleSubmit(event) {
        this.saveComment()
        event.preventDefault()
    }

    saveComment() {
        fetch(api.comment + '/' + this.state.id, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: this.state.id,
                bookId: this.state.bookId,
                name: this.state.name,
                text: this.state.text
            })
        }).then((response) => {
            if (response.ok) {
                this.props.history.push(this.getBackPageWithParams())
            } else {
                response.json().then((error) => {
                    const details = error.details ? error.details.join(', ') : ""
                    alert("Error: " + error.message + "\nDetails: " + details)
                })
            }
        })
    }

    getBackPageWithParams() {
        return this.props.location.search.length > 0
            ? page.comment.root + this.props.location.search
            : page.comment.root
    }

    render() {
        return (
            <Fragment>
                <h2>Edit comment</h2>
                <div className="block">
                    <form className="block-content" onSubmit={this.handleSubmit}>
                        <div className="row">
                            <label>ID:</label>
                            <input type="text" readOnly value={this.state.id} />
                        </div>
                        {this.state.shouldFetchBooks &&
                            <div className="row">
                                <label>Book:</label>
                                <select name={this.book} value={this.state.bookId} onChange={this.handleChange}>
                                    {this.state.books.map((book) => (
                                        <option key={book.id} value={book.id}>{book.name}</option>
                                    ))}
                                </select>
                            </div>
                        }
                        <div className="row">
                            <label>Name:</label>
                            <input type="text" name={this.name} value={this.state.name} onChange={this.handleChange} />
                        </div>
                        <div className="row">
                            <label>Text:</label>
                            <input type="text" name={this.text} value={this.state.text} onChange={this.handleChange} />
                        </div>
                        <div className="row">
                            <input type="submit" value="Save" />
                        </div>
                    </form>
                </div>
            </Fragment>
        )
    }
}
