import styled from "styled-components";
import { useNavigate, useLocation } from "react-router-dom";

const Side = styled.div`
  position: fixed;
  right: 5vw;
  top: 250px;
  z-index: 2;

  @media screen and (max-width: 768px) {
    top: 125px;
    right: 2.5vw;
  }
`;

const Menu = styled.div`
  width: 70px;
  height: 290px;
  border-radius: 10px;
  background-color: #f4eeff;

  @media screen and (max-width: 768px) {
    width: 35px;
    height: 145px;
  }
`;

const ImgDiv = styled.div`
  padding-top: 20px;

  @media screen and (max-width: 768px) {
    padding-top: 10px;
  }
`;

const GoResult = styled.div.attrs(() => ({}))`
  display: block;
  height: 70px;
  background-image: url("/img/management/test.png");
  background-size: 70px;
  cursor: pointer;

  @media screen and (max-width: 768px) {
    height: 35px;
    background-size: 35px;
  }
`;

const GoCurriculum = styled(GoResult).attrs(() => ({}))`
  background-image: url("/img/management/curriculum.png");
`;

const GoSchedule = styled(GoResult).attrs(() => ({}))`
  background-image: url("/img/management/schedule.png");
`;

const ResultSideBar = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const goPage = (e, page) => {
    navigate(page);
  };

  // 주소가 아래와 같을 때만 SiderBar 표시
  if (
    location.pathname === "/time-schedule" ||
    location.pathname === "/curriculum" ||
    location.pathname === "/result"
  ) {
    return (
      <Side>
        <Menu>
          <ImgDiv>
            <GoResult onClick={(e) => goPage(e, "/result")}></GoResult>
          </ImgDiv>
          <ImgDiv>
            <GoCurriculum
              onClick={(e) => goPage(e, "/curriculum")}
            ></GoCurriculum>
          </ImgDiv>
          <ImgDiv>
            <GoSchedule
              onClick={(e) => goPage(e, "/time-schedule")}
            ></GoSchedule>
          </ImgDiv>
        </Menu>
      </Side>
    );
  }
  // 그 외에는 SideBar 미표시
  else {
    return null;
  }
};

export default ResultSideBar;
