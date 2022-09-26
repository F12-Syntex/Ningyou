# Ningyou

Ningyou is a media framework designed to give programmers access to labeled data that has been scraped from a variety of content sources, such as anime, manga, movies, books and more.

# Media
Here is a list of the current media that this framework supports

## Manga
 - MangaStream
 - MangaTo
## Anime 
Currently not supported
## Movies 
Currently not supported
## Books 
Currently not supported
## TV series 
Currently not supported


## Example Usage

```java
import com.ningyou.api.Ningyou;
import com.ningyou.media.Manga;
import com.ningyou.modals.NinjgouList;
import com.ningyou.queries.MangaEntity;

public class Playground {
	
	public static void main(String[] args) {
		 
        //returns the list of mangas found from the query "Attack on titan"
		NinjgouList<MangaEntity> entries = Ningyou.searchManga("attack on titan");
		
        //loop through each manga query
		entries.forEach(mangaQuery -> {
			
            //retrieve the manga from the query Entity
            //query entities are less detailed and only contain basic information about a manga
            //by converting it to a manga, were scraping the contents of main page for this manga, 
            //allowing for much more detailed data
			Manga manga = mangaQuery.asManga();
		
            //print the name of the manga along with how many chapters it has  
			System.out.println(manga.alt + " -> chapters: " + manga.getChapters().size());

		});
		
	}

}
```
