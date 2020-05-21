import React, { Fragment, PureComponent } from 'react'
import { page, api } from './AppContext'

export default class BookAdd extends PureComponent {
    constructor() {
        super()
        this.genre = 'genre'
        this.author = 'author'
        this.name = 'name'
        this.state = { genreId: '', authorId: '', name: '', genres: [], authors: [], shouldFetchGenres: true, shouldFetchAuthors: true }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        const params = new URLSearchParams(this.props.location.search)
        const genreId = params.get('genreId')
        const authorId = params.get('authorId')
        if (genreId) {
            this.setState({ genreId: genreId, shouldFetchGenres: false })
        } else {
            this.fetchGenres()
        }
        if (authorId) {
            this.setState({ authorId: authorId, shouldFetchAuthors: false })
        } else {
            this.fetchAuthors()
        }
    }

    fetchGenres() {
        fetch(api.genre)
            .then((response) => response.json())
            .then((genres) => {
                if (genres.length > 0) {
                    this.setState({ genreId: genres[0].id, genres: genres })
                }
            })
            .catch((error) => console.error(error))
    }

    fetchAuthors() {
        fetch(api.author)
            .then((response) => response.json())
            .then((authors) => {
                if (authors.length > 0) {
                    this.setState({ authorId: authors[0].id, authors: authors })
                }
            })
            .catch((error) => console.error(error))
    }

    handleChange(event) {
        switch (event.target.name) {
            case this.genre:
                this.setState({ genreId: event.target.value })
                break
            case this.author:
                this.setState({ authorId: event.target.value })
                break
            case this.name:
                this.setState({ name: event.target.value })
                break
            default:
                console.warn("Incorrect name=" + event.target.name)
        }
    }

    handleSubmit(event) {
        this.saveBook()
        event.preventDefault()
    }

    saveBook() {
        fetch(api.book, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                genreId: this.state.genreId,
                authorId: this.state.authorId,
                name: this.state.name
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
            ? page.book.root + this.props.location.search
            : page.book.root
    }

    render() {
        return (
            <Fragment>
                <h2>Add book</h2>
                <div className="block">
                    <form className="block-content" onSubmit={this.handleSubmit}>
                        {this.state.shouldFetchGenres &&
                            <div className="row">
                                <label>Genre:</label>
                                <select name={this.genre} value={this.state.genreId} onChange={this.handleChange}>
                                    {this.state.genres.map((genre) => (
                                        <option key={genre.id} value={genre.id}>{genre.name}</option>
                                    ))}
                                </select>
                            </div>
                        }
                        {this.state.shouldFetchAuthors &&
                            <div className="row">
                                <label>Author:</label>
                                <select name={this.author} value={this.state.authorId} onChange={this.handleChange}>
                                    {this.state.authors.map((author) => (
                                        <option key={author.id} value={author.id}>{author.name}</option>
                                    ))}
                                </select>
                            </div>
                        }
                        <div className="row">
                            <label>Name:</label>
                            <input type="text" name={this.name} value={this.state.name} onChange={this.handleChange} />
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
