package com.mashuq.athenaeum.configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LuceneConfiguration {

	@Value("${lucene.index.location}")
	private String indexPath;

	@Bean
	public Directory getIndex() throws IOException, URISyntaxException {
		return FSDirectory.open(Paths.get(new URI(indexPath)));
	}

	@Bean
	public Analyzer getAnalyzer() {
		return new StandardAnalyzer();
	}

	@Bean
	@Scope("prototype")
	public IndexWriterConfig getWriterConfiguration() {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				getAnalyzer());
		indexWriterConfig
				.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		return indexWriterConfig;
	}

	@Bean
	@Scope("prototype")
	public IndexWriter getIndexWriter() throws IOException, URISyntaxException {
		return new IndexWriter(getIndex(), getWriterConfiguration());
	}

	@Bean
	public IndexReader getIndexReader() throws IOException, URISyntaxException {
		return DirectoryReader.open(getIndex());
	}

	@Bean
	public IndexSearcher getIndexSearcher()
			throws IOException, URISyntaxException {
		return new IndexSearcher(getIndexReader());
	}

}
