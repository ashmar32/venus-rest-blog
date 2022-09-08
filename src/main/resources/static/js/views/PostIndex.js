import CreateView from "../createView.js";
import createView from "../createView.js";
export default function PostIndex(props) {
    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>
              <h3>Lists of posts</h3>
            <div>
                ${props.posts.map(post => `<h3>${post.title}</h3>`).join('')} 
                <button id="editPost" name="editPost">Edit Post</button>  
            </div>
            
            <h3>Add a post</h3>
            <form>
                <label for="title">Title</label><br>
                <input id="title" name="title" type="text" placeholder="Enter title">
                <br>
                <label for="content">Content</label><br>
                <textarea id="content" name="content" rows="10" cols="50" placeholder="Enter content"></textarea>
                <button id="addPost" name="addPost">Add Post</button>
            </form>
            
        </main>
    `;
}



export function postSetup() {
    addPostHandler();
    editPostHandler();
    // deletePostHandler setup
}

function editPostHandler() {
    const editButton = document.querySelector("#editPost")
}

function addPostHandler() {
    const addButton = document.querySelector("#addPost");
    addButton.addEventListener("click", function(event) {
        const titleField = document.querySelector("#title");
        const contentField = document.querySelector("#content");

        let newPost = {
            title: titleField.value,
            content: contentField.value
        }

        let request = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(newPost)
        }

        fetch("http://localhost:8080/api/posts", request)
            .then(response => {
                console.log(response.status);
                CreateView("/posts");
            }).catch(error => {
            console.log(error);
            createView("/posts")
        })
    });
}