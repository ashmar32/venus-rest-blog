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
<!--                <button id="editPost" name="editPost">Edit Post</button>-->
<!--                <button id="deletePost" name="deletePost">Delete Post</button>  -->
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




export function postSetup() {
    savePostHandler();
    editPostHandler();
    setupDeletePostHandler();
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
        const url = POST_API_BASE_URL + `${postId}`
    }
}


function editPostHandler() {
    const editButton = document.querySelectorAll("#editPost");
    editButton.forEach(function (btn) {
        btn.addEventListener("click", function (event) {
            console.log("this edit button working?")
        })
    })

}

function savePostHandler() {
    const saveButton = document.querySelector("#savePost");
    saveButton.addEventListener("click", function(event) {
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