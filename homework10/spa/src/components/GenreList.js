import React, { Fragment, PureComponent } from 'react'
import { Link } from "react-router-dom"
import { page, api } from './AppContext'

export default class GenreList extends PureComponent {
    constructor() {
        super()
        this.state = { genres: [] }
    }

    componentDidMount() {
        this.fetchGenres()
    }

    fetchGenres() {
        fetch(api.genre)
            .then((response) => response.json())
            .then((genres) => this.setState({ genres }))
            .catch((error) => console.error(error))
    }

    deleteGenre(genreId) {
        fetch(api.genre + "/" + genreId, { method: 'DELETE' })
            .then((response) => {
                if (response.ok) {
                    this.setState({ genres: this.state.genres.filter(genre => genre.id !== genreId) })
                } else {
                    response.json().then((error) => {
                        const details = error.details ? error.details.join(', ') : ""
                        alert("Error: " + error.message + "\nDetails: " + details)
                    })
                }
            })
    }

    getEditPageWithParams(genreId) {
        return page.genre.edit + '/' + genreId
    }

    getBookPageWithParams(genreId) {
        return page.book.root + "?genreId=" + genreId
    }

    render() {
        return (
            <Fragment>
                <h2>List of genres</h2>
                <div className="block">
                    <Link to={page.genre.add}>Add genre</Link>
                </div>
                {this.state.genres.length > 0 ?
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
                                {this.state.genres.map((genre) => (
                                    <tr key={genre.id}>
                                        <td>{genre.id}</td>
                                        <td>{genre.name}</td>
                                        <td>
                                            <Link to={this.getEditPageWithParams(genre.id)}>Edit</Link>
                                            <span> | </span>
                                            <a id="delete" href="#delete" onClick={() => { this.deleteGenre(genre.id) }}>Delete</a>
                                            <span> | </span>
                                            <Link to={this.getBookPageWithParams(genre.id)}>Books</Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    : <div className="block">No genres</div>}
            </Fragment>
        )
    }
}
