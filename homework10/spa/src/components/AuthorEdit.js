import React, { Fragment, PureComponent } from 'react'
import { page, api } from './AppContext'

export default class AuthorEdit extends PureComponent {
    constructor() {
        super()
        this.state = { id: '', name: '' }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        this.fetchAuthor()
    }

    fetchAuthor() {
        fetch(api.author + '/' + this.props.match.params.id)
            .then((response) => response.json())
            .then((author) => this.setState({ id: author.id, name: author.name }))
            .catch((error) => console.error(error))
    }

    handleChange(event) {
        this.setState({ name: event.target.value })
    }

    handleSubmit(event) {
        this.saveAuthor()
        event.preventDefault()
    }

    saveAuthor() {
        fetch(api.author + '/' + this.state.id, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: this.state.id,
                name: this.state.name
            })
        }).then((response) => {
            if (response.ok) {
                this.props.history.push(page.author.root)
            } else {
                response.json().then((error) => {
                    const details = error.details ? error.details.join(', ') : ""
                    alert("Error: " + error.message + "\nDetails: " + details)
                })
            }
        })
    }

    render() {
        return (
            <Fragment>
                <h2>Edit author</h2>
                <div className="block">
                    <form className="block-content" onSubmit={this.handleSubmit}>
                        <div className="row">
                            <label>ID:</label>
                            <input type="text" readOnly value={this.state.id} />
                        </div>
                        <div className="row">
                            <label>Name:</label>
                            <input type="text" value={this.state.name} onChange={this.handleChange} />
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
