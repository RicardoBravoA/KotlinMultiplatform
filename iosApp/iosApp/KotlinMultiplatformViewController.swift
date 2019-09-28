import UIKit
import app

class KotlinMultiplatformViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
    
    @IBOutlet var movieCollectionView: UICollectionView!
    internal var movieList: [MovieItemResponse] = []
    
    internal var movieApi = MovieApi()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        movieCollectionView.dataSource = self
        movieCollectionView.delegate = self
        loadList()
    }
    
    private func loadList(){
        movieApi.getMovieList(
            success: { data in
                self.update(data: data)
                return KotlinUnit()
        }, failure: {
            self.handleError($0?.message)
            return KotlinUnit()
        })
    }
    
    internal func update(data: [MovieItemResponse]) {
        movieList.removeAll()
        movieList.append(contentsOf: data)
        movieCollectionView.reloadData()
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return movieList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = movieCollectionView.dequeueReusableCell(withReuseIdentifier: "movieCell", for: indexPath) as! KotlinMultiplatformTableViewCell
        
        cell.labelCell.text = movieList[indexPath.row].title
        //cell.imageCell.image = UIImage(named: movieList[indexPath.row].poster_path)
        //loadMovieImage(path: self.movieList[indexPath.row].poster_path, imageView: cell.imageCell)
        
        let stringUrl = "https://image.tmdb.org/t/p/w185/\(self.movieList[indexPath.row].poster_path)"
        let url = URL(string: stringUrl)
        let data = try? Data(contentsOf: url!)
        cell.imageCell.image = UIImage(data: data!)
        
        return cell
    }
    
    internal func handleError(_ error: String?){
        let message = error ?? "An unknown error occurred. Retry?"
        let alert = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        self.present(alert, animated: true)
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 190
    }
    
}
