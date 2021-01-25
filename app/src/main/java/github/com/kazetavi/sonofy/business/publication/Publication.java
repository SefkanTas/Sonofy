package github.com.kazetavi.sonofy.business.publication;

import github.com.kazetavi.sonofy.business.Authorable;
import github.com.kazetavi.sonofy.business.image.Pictureable;
import github.com.kazetavi.sonofy.business.opinion.Dislikeable;
import github.com.kazetavi.sonofy.business.opinion.Likeable;

public interface Publication extends Authorable, Likeable, Dislikeable, Pictureable{
}
