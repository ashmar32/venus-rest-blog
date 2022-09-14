import CreateView from "../createView.js";
import {getHeaders} from "../auth.js";

let posts;
export default function PostIndex(props) {
    posts = props.posts;
    const postsHTML = generatePostsHTML(props.posts);
    // save this for loading edits later

    console.log(posts)

    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>
              <h3>Lists of posts</h3>
            <div>
                ${postsHTML}   
            </div>
            
            <h3>Add a post</h3>
            <form>
                <label for="title">Title</label><br>
                <input id="title" name="title" type="text" placeholder="Enter title">
                <br>
                <label for="content">Content</label><br>
                <textarea id="content" name="content" rows="10" cols="50" placeholder="Enter content"></textarea>
                <br>
                <button data-id="0" id="savePost" name="savePost" class="button btn-primary">Save Post</button>
            </form>
            
        </main>
    `;
}

function generatePostsHTML() {
    let postsHTML = `
        <table class="table">
        <thead>
        <tr>
            <th scope="col">Title</th>
            <th scope="col">Content</th>
            <th scope="col">Author</th>
        </tr>
        </thead>
        <tbody>
    `;
    console.log(posts)
    for (let i = 0; i < posts.length; i++) {
        const post = posts[i];
        postsHTML += `<tr>
            <td>${post.title}</td>
            <td>${post.content}</td>
            <td><button data-id=${post.id} class="button btn-primary editPost">Edit</button></td>
            <td><button data-id=${post.id} class="button btn-danger deletePost">Delete</button></td>
            </tr>`;
    }
    postsHTML += `</tbody></table>`;
    return postsHTML;
}



export function postSetup() {
    setupSaveHandler();
    setupEditHandlers();
    setupDeletePostHandler();
}


function setupEditHandlers() {
    // target all delete buttons
    const editButtons = document.querySelectorAll(".editPost");
    // add click handler to all delete buttons
    for (let i = 0; i < editButtons.length; i++) {
        editButtons[i].addEventListener("click", function(event) {

            // get the post id of the delete button
            const postId = parseInt(this.getAttribute("data-id"));

            loadPostIntoForm(postId);
        });
    }
}

function loadPostIntoForm(postId) {
    // go find the post in the posts data that matches postId
    const post = fetchPostById(postId);
    if (!post) {
        console.log("did not find post for id " + postId);
        return;
    }

    // load post data into the form
    const titleField = document.querySelector("#title");
    const contentField = document.querySelector("#content");
    titleField.value = post.title;
    contentField.value = post.content;

    const saveButton = document.querySelector("#savePost");
    saveButton.setAttribute("data-id", postId);
}

function fetchPostById(postId) {
    for (let i = 0; i < posts.length; i++) {
        if (posts[i].id === postId) {
            return posts[i];
        }
    }
    return false;
}

function setupDeletePostHandler() {
    // target all delete buttons
    const deleteButtons = document.querySelectorAll(".deletePost")
    //add delete button to each post
    for (let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener("click", function (event) {
            console.log("is the delete button setup?")
            // get the post id of the delete button
            const postId = this.getAttribute("data-id");
            deletePost(postId);
        });
    }
}

function deletePost(postId) {
    const request = {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    }
    let url = "http://localhost:8080/api/posts/" + `${postId}`;
    fetch(url, request)
        .then(function (response) {
            if(response.status !== 200) {
                console.log("fetch returned bad status code: " + response.status);
                console.log(response.statusText);
                return;
            }
            CreateView("/posts");
        })
}

function setupSaveHandler() {
    const saveButton = document.querySelector("#savePost");
    saveButton.addEventListener("click", function(event) {
        console.log(this.getAttribute("data-id"));
        const postId = parseInt(this.getAttribute("data-id"));
        savePost(postId);
    });
}

function savePost(postId) {
    // get the title and content for the new/updated post
    const titleField = document.querySelector("#title");
    const contentField = document.querySelector("#content");

    // make the new/updated post object
    const post = {
        title: titleField.value,
        content: contentField.value
    }

    // make the request
    const request = {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(post)
    }
    let url = POST_API_BASE_URL;

    // if we are updating a post, change the request and the url
    if(postId > 0) {
        request.method = "PUT";
        url = url + `/${postId}`;
    }

    fetch(url, request)
        .then(function(response) {
            if(response.status !== 200) {
                console.log("fetch returned bad status code: " + response.status);
                console.log(response.statusText);
                return;
            }
            CreateView("/posts");
        })
}
