package org.jabref.logic.importer.fetcher;

import java.util.Collections;
import java.util.List;

import org.jabref.logic.importer.FetcherException;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.testutils.category.FetcherTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FetcherTest
public class DOABFetcherTest {
    private DOABFetcher fetcher;
    private BibEntry DavidPolEntry;
    private BibEntry RonaldSnijderEntry;
    private BibEntry AndrewAndLorenEntry;

    @BeforeEach
    public void setUp() throws Exception {
        fetcher = new DOABFetcher();

        DavidPolEntry = new BibEntry();
        DavidPolEntry.setField(StandardField.AUTHOR, "David Pol");
        DavidPolEntry.setField(StandardField.TITLE, "I Open Fire");
        DavidPolEntry.setField(StandardField.TYPE, "book");
        DavidPolEntry.setField(StandardField.DOI, "10.21983/P3.0086.1.00");
        DavidPolEntry.setField(StandardField.PAGES, "56");
        DavidPolEntry.setField(StandardField.YEAR, "2014");
        DavidPolEntry.setField(StandardField.URI, "https://directory.doabooks.org/handle/20.500.12854/34739");
        DavidPolEntry.setField(StandardField.ABSTRACT, "David Pol presents an ontology of war in the form of " +
                "the lyric poem. “Do you hear what I’m shooting at you?” In I Open Fire, all relation is " +
                "warfare. Minefields compromise movement. Intention aims. Touch burns. Sex explodes bodies. " +
                "Time ticks in bomb countdowns. Sound is sirens. Plenitude is debris. All of it under " +
                "surveillance. “My world is critically injured. It was ambushed.” The poems in this book perform" +
                " the reductions and repetitions endemic to war itself, each one returning the reader to the same," +
                " unthinkable place in which the range of human experience has been so flattened that, despite all" +
                " the explosive action, “Almost nothing is happening.” Against this backdrop, we continue to fall" +
                " in love. But Pol’s poems remind us that this is no reason for optimism. Does love offer a" +
                " delusional escape from war, or are relationships the very definition of combat? These poems take" +
                " up the themes of love, sex, marriage, touch, hope — in short, the many dimensions of" +
                " interpersonal connection — in a world in unprecedentedly critical condition. “And when the night" +
                " goes off the shock wave throws us apart toward each other.”");
        DavidPolEntry.setField(StandardField.LANGUAGE, "English");
        DavidPolEntry.setField(StandardField.KEYWORDS, "poetry, love, warfare");
        DavidPolEntry.setField(StandardField.PUBLISHER, "punctum books");

        RonaldSnijderEntry = new BibEntry();
        RonaldSnijderEntry.setField(StandardField.AUTHOR, "Ronald Snijder");
        RonaldSnijderEntry.setField(StandardField.TITLE, "The deliverance of open access books");
        RonaldSnijderEntry.setField(StandardField.TYPE, "book");
        RonaldSnijderEntry.setField(StandardField.DOI, "10.26530/OAPEN_1004809");
        RonaldSnijderEntry.setField(StandardField.PAGES, "234");
        RonaldSnijderEntry.setField(StandardField.YEAR, "2019");
        RonaldSnijderEntry.setField(StandardField.URI, "https://directory.doabooks.org/handle/20.500.12854/26303");
        RonaldSnijderEntry.setField(StandardField.ABSTRACT, "In many scholarly disciplines, books - not articles" +
                " - are the norm. As print runs become smaller, the question arises whether publishing monographs" +
                " in open access helps to make their contents globally accessible. To answer this question, the" +
                " results of multiple studies on the usage of open access books are presented. The research" +
                " focuses on three areas: economic viability; optimization of open access monographs" +
                " infrastructure and measuring the effects of open access in terms of scholarly impact and" +
                " societal influence. Each chapter reviews a different aspect: book sales, digital dissemination," +
                " open licenses, user communities, measuring usage, developing countries and the effects on" +
                " citations and social media.");
        RonaldSnijderEntry.setField(StandardField.LANGUAGE, "English");
        RonaldSnijderEntry.setField(StandardField.KEYWORDS, "Open Access, Monographs, OAPEN Library, " +
                "Directory of Open Access Books");
        RonaldSnijderEntry.setField(StandardField.PUBLISHER, "Amsterdam University Press");

        AndrewAndLorenEntry = new BibEntry();
        AndrewAndLorenEntry.setField(StandardField.EDITOR, "Andrew Perrin and Loren T. Stuckenbruck");
        AndrewAndLorenEntry.setField(StandardField.TITLE, "Four Kingdom Motifs before and beyond the Book of Daniel");
        AndrewAndLorenEntry.setField(StandardField.TYPE, "book");
        AndrewAndLorenEntry.setField(StandardField.DOI, "10.1163/9789004443280");
        AndrewAndLorenEntry.setField(StandardField.PAGES, "354");
        AndrewAndLorenEntry.setField(StandardField.YEAR, "2020");
        AndrewAndLorenEntry.setField(StandardField.URI, "https://directory.doabooks.org/handle/20.500.12854/68086");
        AndrewAndLorenEntry.setField(StandardField.ABSTRACT, "The four kingdoms motif enabled writers of various " +
                "cultures, times, and places, to periodize history as the staged succession of empires " +
                "barrelling towards an utopian age. The motif provided order to lived experiences under empire" +
                " (the present), in view of ancestral traditions and cultural heritage (the past), and inspired" +
                " outlooks assuring hope, deliverance, and restoration (the future). Four Kingdom Motifs before" +
                " and beyond the Book of Daniel includes thirteen essays that explore the reach and redeployment" +
                " of the motif in classical and ancient Near Eastern writings, Jewish and Christian scriptures," +
                " texts among the Dead Sea Scrolls, Apocrypha and pseudepigrapha, depictions in European" +
                " architecture and cartography, as well as patristic, rabbinic, Islamic, and African writings " +
                "from antiquity through the Mediaeval eras. Readership: Advanced students and scholars of the " +
                "textual formation, apocalyptic theology, and historiographies of the book of Daniel and its " +
                "diverse reception by writers and communities.");
        AndrewAndLorenEntry.setField(StandardField.LANGUAGE, "English");
        AndrewAndLorenEntry.setField(StandardField.KEYWORDS, "Religion");
        AndrewAndLorenEntry.setField(StandardField.PUBLISHER, "Brill");
    }

    @Test
    public void TestGetName() {
        assertEquals("DOAB", fetcher.getName());
    }

    @Test
    public void TestPerformSearch() throws FetcherException {
        List<BibEntry> entries;
        entries = fetcher.performSearch("i open fire");
        assertEquals(Collections.singletonList(DavidPolEntry), entries);
    }

    @Test
    public void TestPerformSearch2() throws FetcherException {
        List<BibEntry> entries;
        entries = fetcher.performSearch("the deliverance of open access books");
        assertEquals(Collections.singletonList(RonaldSnijderEntry), entries);
    }

    @Test
    public void TestPerformSearch3() throws FetcherException {
        List<BibEntry> entries;
        entries = fetcher.performSearch("Four Kingdom Motifs before and beyond the Book of Daniel");
        assertEquals(Collections.singletonList(AndrewAndLorenEntry), entries);
    }
}