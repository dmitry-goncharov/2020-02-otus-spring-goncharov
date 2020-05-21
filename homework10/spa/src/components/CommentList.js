import React, { Fragment, PureComponent } from 'react'
import { Link } from "react-router-dom"
import { page, api } from './AppContext'

export default class CommentList extends PureComponent {
    constructor() {
        super()
        this.state = { comments: [], book: null }
    }

    componentDidMount() {
        this.fetchComments()
        const params = new URLSearchParams(this.props.location.search)
        if (params.get('bookId')) {
            this.fetchBook(params.get('bookId'))
        }
    }

    fetchComments() {
        fetch(api.comment + this.props.location.search)
            .then((response) => response.json())
            .then((comments) => this.setState({ comments }))
            .catch((error) => console.error(error))
    }

    fetchBook(bookId) {
        fetch(api.book + '/' + bookId)
            .then((response) => response.json())
            .then((book) => this.setState({ book }))
            .catch((error) => console.error(error))
    }

    deleteComment(commentId) {
        fetch(api.comment + "/" + commentId, { method: 'DELETE' })
            .then((response) => {
                if (response.ok) {
                    this.setState({ comments: this.state.comments.filter(comment => comment.id !== commentId) })
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
            ? page.comment.add + this.props.location.search
            : page.comment.add
    }

    getEditPageWithParams(commentId) {
        return this.props.location.search.length > 0
            ? page.comment.edit + '/' + commentId + this.props.location.search
            : page.comment.edit + '/' + commentId
    }

    render() {
        return (
            <Fragment>
                <h2>List of comments {this.state.book && <span>by book "{this.state.book.name}"</span>}</h2>
                <div className="block">
                    <Link to={this.getAddPageWithParams()}>Add comment</Link>
                </div>
                {this.state.comments.length > 0 ?
                    <div className="block">
                        <table className="block-content">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Text</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.comments.map((comment) => (
                                    <tr key={comment.id}>
                                        <td>{comment.id}</td>
                                        <td>{comment.name}</td>
                                        <td>{comment.text}</td>
                                        <td>
                                            <Link to={this.getEditPageWithParams(comment.id)}>Edit</Link>
                                            <span> | </span>
                                            <a id="delete" href="#delete" onClick={() => { this.deleteComment(comment.id) }}>Delete</a>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    : <div className="block">No comments</div>}
            </Fragment>
        )
    }
}
