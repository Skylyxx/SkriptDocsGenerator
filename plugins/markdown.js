export default ({ app }, inject) => {
  // Inject $hello(msg) in Vue, context and store.
  inject("markdown", msg => {
    msg = msg.split("\r\n\r\n").join("\r\n");
    let array = msg.split("\r\n");
    array.forEach((element, index) => {
      element = element
        .replace(/^###(.*)/gim, "<h3>$1</h3>")
        .replace(/^##(.*)/gim, "<h2>$1</h2>")
        .replace(/^#(.*)/gim, "<h1>$1</h1>")
        .replace(/^-(.+)/gim, "· $1")
        .replace(/\*\*(.*?)\*\*/gim, "<b>$1</b>")
        .replace(/\[(.*?)\]\((.*?)\)/gim, "<a href='$2' class='text-green-500 hover:text-white hover:underline' target='_blank'>$1</a>");

      array[index] = element + "<br />";
    });
    // console.log(array);
    return array.join("");
  });
};
