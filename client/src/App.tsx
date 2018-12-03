import axios from 'axios'
import * as React from 'react';
import './App.css';


class App extends React.Component<{}, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            selectedFile: null,
        };
    }

    public render() {
        return (
            <div>
                <form onSubmit={this.submitHandler}>
                    <input type="file" onChange={this.onChangeHandler}/>
                    <button>Upload</button>
                </form>
            </div>
        );
    }

    private onChangeHandler = (event: any) => {
        this.setState({
            selectedFile: event.target.files[0]
        })
    };

    private submitHandler = (event: any) => {
        event.preventDefault();
        if (this.state.selectedFile != null) {
            const data = new FormData();
            data.append('file', this.state.selectedFile, this.state.selectedFile.name);
            // axios.post('http://localhost:8080/operations/', data, {})
            axios.post('http://ec2-54-165-176-95.compute-1.amazonaws.com:8080/operations/', data, {})
        }
    }
}

export default App;
